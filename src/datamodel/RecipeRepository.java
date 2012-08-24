package datamodel;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import utility.CursorHelper;
import utility.Selector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import beerxml.FERMENTABLE;
import beerxml.HOP;
import beerxml.MISC;
import beerxml.RECIPE;
import beerxml.RECIPES;
import beerxml.STYLE;
import beerxml.YEAST;

import com.example.homebrewnavigator.MyContext;
import com.example.homebrewnavigator.bll.ManualRecipeStep;
import com.example.homebrewnavigator.bll.Recipe;
import com.example.homebrewnavigator.bll.TemperatureStep;
import com.example.homebrewnavigator.bll.TimedStep;

import db.ContentValueBuilder;

public class RecipeRepository implements Comparator<RECIPE>{

	public static SQLiteOpenHelper _db;
	
	public RecipeRepository(SQLiteOpenHelper db) {
		_db = db;
	}
	
	private static List<RECIPE> mRecipes;
	private List<RECIPE> getRecipes() {
		if (mRecipes == null)
		{
			try{
				Context ctx = MyContext.getContext();
				int id = ctx.getResources().getIdentifier("recipes", "raw",
						ctx.getPackageName());
				
				InputStream contents = ctx.getResources().openRawResource(id);
				Serializer serializer = new Persister();
				mRecipes = serializer.read(RECIPES.class, contents).gettheRecipes();
				contents.close();

				Collections.sort(mRecipes, this);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return mRecipes;
	}
	
	
	public RECIPE recipeForName(String name) {
		for(RECIPE r:getRecipes()){
			if (r.getNAME().equals(name)){
				return r;
			}
		}
		return null;
	}

	
	public List<String> getCategories() {
		Cursor c = _db.getReadableDatabase().rawQuery("select distinct category from styles order by category", null);
		
		return CursorHelper.loop(c, new Selector<String, Cursor>(){
			@Override
			public String map(Cursor c) { return c.getString(0); }
		});
	}

	public List<RecipeManagerViewModel> recipesForCategory(String category) {
		Cursor c = _db.getReadableDatabase().rawQuery(
				"select r.name, s.ibu_min, s.ibu_max, s.abv_min, s.abv_max from recipes r join styles s on s.recipe_id = r.id where s.category = ? order by r.name",
				new String[]{category});
		
		return CursorHelper.loop(c, new Selector<RecipeManagerViewModel, Cursor>(){
			@Override
			public RecipeManagerViewModel map(Cursor c) {
				RecipeManagerViewModel r = new RecipeManagerViewModel();
				r.Name = c.getString(0);
				r.IbuMin = c.getDouble(1);
				r.IbuMax = c.getDouble(2);
				r.AbvMin = c.getDouble(3);
				r.AbvMax = c.getDouble(4);
				return r;
			}
		});
	}

	public RecipeViewModel recipeForName2(final String name) {
		Cursor recipeIdCursor = _db.getReadableDatabase().rawQuery(
				"select r.id, r.batch_size, r.boil_size, r.notes, r.type, s.og_max, s.og_min, s.fg_max, s.fg_min, s.ibu_max, s.ibu_min, s.abv_max, s.abv_min, s.name from recipes r join styles s on r.id = s.recipe_id where name = ?",
				new String[]{name});
		
		RecipeViewModel recipe = CursorHelper.first(recipeIdCursor, new Selector<RecipeViewModel, Cursor>(){
			@Override
			public RecipeViewModel map(Cursor c) {
				RecipeViewModel r = new RecipeViewModel();
				int i = 0;
				r.Id = c.getLong(i++);
				r.Name = name;
				r.BatchSize = c.getDouble(i++);
				r.BoilSize = c.getDouble(i++);
				r.Notes = c.getString(i++);
				r.Type = c.getString(i++);
				r.OgMax = c.getDouble(i++);
				r.OgMin = c.getDouble(i++);
				r.FgMax = c.getDouble(i++);
				r.FgMin = c.getDouble(i++);
				r.IbuMax = c.getDouble(i++);
				r.IbuMin = c.getDouble(i++);
				r.AbvMax = c.getDouble(i++);
				r.AbvMin = c.getDouble(i++);
				r.StyleName = c.getString(i++);
				r.Category = c.getString(i++);
				return r;
			}
		});
		
		recipe.Ingredients = getIngredientsText(recipe.Id);
		recipe.Instructions = getInstructionsText(recipe.Id);
		
		return recipe;
	}
	
	public Recipe getDeepRecipe(String name) {
		RECIPE theRecipe = recipeForName(name);

		Recipe toBrew = new Recipe();
		toBrew.setName(name);
		toBrew.addStep(new ManualRecipeStep("(Optional) rehydate irish moss in 1/2c water."));
		toBrew.addStep(new TemperatureStep(150, "\u00B0F", 0, "Heat your water", false));
		toBrew.addStep(new TimedStep(30, "Steep grains for 30 minutes"));
		toBrew.addStep(new ManualRecipeStep("Add your extracts."));
		toBrew.addStep(new TemperatureStep(212, "\u00B0F", 0, "Raise to a boil",false));
		
		List<StepPair> pairs = new ArrayList<StepPair>();
		
		toBrew.addStep(new TimedStep((int) theRecipe.getBOIL_TIME(), "Boil for 60 minutes", true));
		
		for(HOP h:theRecipe.getHOPS().gettheHops()) {
			pairs.add(new StepPair("Add " + h.getNAME() + " hops", 60-h.getTIME()));
		}	
		if (theRecipe.getMISCS().gettheMiscs() != null) {
			for(MISC m:theRecipe.getMISCS().gettheMiscs())
				pairs.add(new StepPair("Add " + m.getNAME(), m.getTIME()));
		}
		pairs.add(new StepPair("(Optional) Place wort chiller in wort", 50));

		Collections.sort(pairs, new StepComparator());

		int boil = 0;
		for(StepPair p:pairs) {
			double time = p.getValue() - boil;
			
			if (time !=0)
				toBrew.addStep(new TimedStep((int)time, "Boil for " + time + " minutes"));
			toBrew.addStep(new ManualRecipeStep(p.getText()));
			
			boil += time;
		}
		
		toBrew.addStep(new TemperatureStep(80, "\u00B0F", 212, "Chill Wort", true));
		toBrew.addStep(new ManualRecipeStep("Add Yeast"));
		toBrew.addStep(new ManualRecipeStep("Move to Fermenter"));
		return toBrew;
	}

	public class StepComparator implements Comparator<StepPair> {

		@Override
		public int compare(StepPair arg0, StepPair arg1) {
			if (arg0 == null && arg1 == null)
				return 0;
			if (arg0 == null)
				return -1;
			if (arg1 == null)
				return 1;
			return (int) Math.round(arg0.mValue - arg1.mValue);
		}
	}

	public class StepPair {
		double mValue; 
		String mText;
		
		public StepPair(String text, double value){
			mText = text;
			mValue = value;
		}
		
		public String getText() {
			return mText;
		}
		public double getValue() {
			return mValue;
		}
	}
		
	@Override
	public int compare(RECIPE lhs, RECIPE rhs) {
		return lhs.getNAME().compareToIgnoreCase(rhs.getNAME());
	}
	
	private String getInstructionsText(long id) {
		return "instructions here!!"; // TODO
	}

	private String getIngredientsText(long recipeId) {
		final StringBuilder builder = new StringBuilder();

		Cursor hc = _db.getReadableDatabase().rawQuery(
				"select amount, name, time from hops where recipe_id = ?",
				new String[]{Long.toString(recipeId)});

		CursorHelper.loop(hc, new Selector<Void, Cursor>() {
			@Override
			public Void map(Cursor c) {
				int i = 0;
				builder.append((round(c.getDouble(i++)*35.274, 2)) + "oz. " + c.getString(i++) + " at " + Math.round(c.getInt(i++)) + "\n");
				return null;
			}
		});

		Cursor fc = _db.getReadableDatabase().rawQuery(
				"select amount, name from fermentables where recipe_id = ?",
				new String[]{Long.toString(recipeId)});

		CursorHelper.loop(fc, new Selector<Void, Cursor>() {
			@Override
			public Void map(Cursor c) {
				int i = 0;
				builder.append(round(c.getDouble(i++)*2.20462, 2) + "lbs " + c.getString(i++) + "\n");
				return null;
			}
		});

		// TODO
//		if (recipeId.getMISCS().gettheMiscs() != null) {
//			for(MISC m:recipeId.getMISCS().gettheMiscs()){
//				Boolean isWeight = m.getAMOUNT_IS_WEIGHT().equals("TRUE");
//				String qty = round(m.getAMOUNT() * (isWeight ? 2.20462 : 33.814), 2) + "";
//				String unit = isWeight ? "lbs" : "oz";
//				String time = (m.getTIME() == 0)  ? "" : " at " + m.getTIME(); // TODO: what if the value actually is zero?
//				
//				ingredients += qty + " " + unit + " " + m.getNAME() + time + "\n";
//			}
//		}
//		
//		ingredients += "\n";
//		
//		for(YEAST y:recipeId.getYEASTS().gettheYeasts()) {
//			ingredients += "Yeast: " + y.getNAME() + " " + y.getPRODUCT_ID() + "\n";
//		}
		
		return builder.toString();
	}
	
	String round(double d, int numDecimals) {
        DecimalFormat twoDForm = new DecimalFormat("#." + makeString("#", numDecimals));
        return twoDForm.format(d);
	}
        
    String makeString(String str, int num) {
    	String retValue = "";
    	for(int i=0; i<num; i++)
    		retValue += str;
    	return retValue;
    }
	
}
