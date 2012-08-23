package beerxml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
	
	public Boolean ImportRecipesFromXml(InputStream xmlStream) {
		try {
			Serializer serializer = new Persister();
			List<RECIPE> recipes = serializer.read(RECIPES.class,  xmlStream).gettheRecipes();
			for (RECIPE recipe : recipes) {
				InsertRecipe(recipe);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean getBool(String boolStr) {
		return boolStr == null ? false : boolStr.equalsIgnoreCase("TRUE");
	}
	
	private double getDouble(String doubleStr) {
		try {
			return Double.parseDouble(doubleStr);
		} catch (Exception e) {
			return 0;
		}
	}
	
	private void InsertRecipe(RECIPE recipe) {
		SQLiteDatabase db = _db.getWritableDatabase();
		try {
			db.beginTransaction();
			long recipeId = _db.getWritableDatabase().insert("recipes", null, ContentValueBuilder.Create()
					.String("asst_brewer", recipe.getASST_BREWER())
					.String("brewer", recipe.getBREWER())
					.String("date", recipe.getDATE())
					.Boolean("forced_carbonation",  getBool(recipe.getFORCED_CARBONATION()))
					.Integer("age", (int)recipe.getAGE())
					.Integer("age_temp", (int)recipe.getAGE_TEMP())
					.Double("batch_size", recipe.getBATCH_SIZE())
					.Double("boil_size", recipe.getBOIL_SIZE())
					.Integer("carbonation", (int)recipe.getCARBONATION())
					.Integer("boil_time", (int)recipe.getBOIL_TIME())
					.String("date", recipe.getDATE())
					.Double("efficiency", recipe.getEFFICIENCY())
					.Integer("fermentation_stages", (int)recipe.getFERMENTATION_STAGES())
					.Double("fg", recipe.getFG())
					.Double("og", recipe.getOG())
					.Double("keg_priming_factor", recipe.getKEG_PRIMING_FACTOR())
					.String("name", recipe.getNAME())
					.String("notes", recipe.getNOTES())
					.Integer("primary_age", (int)recipe.getPRIMARY_AGE())
					.Integer("primary_temp", (int)recipe.getPRIMARY_TEMP())
					.Integer("secondary_age", (int)recipe.getSECONDARY_AGE())
					.Integer("secondary_temp", (int)recipe.getSECONDARY_TEMP())
					.Integer("tertiary_age", (int)recipe.getTERTIARY_AGE())
					.Integer("tertiary_temp", (int)recipe.getTERTIARY_TEMP())
					.Double("priming_sugar_equiv", recipe.getPRIMING_SUGAR_EQUIV())
					.String("priming_sugar_name", recipe.getPRIMING_SUGAR_NAME())
					.String("taste_notes", recipe.getTASTE_NOTES())
					.Double("taste_rating", recipe.getTASTE_RATING())
					.String("type", recipe.getTYPE())
					.Double("version", recipe.getVERSION())
					.getValues());
			
			for (HOP hop : recipe.getHOPS().gettheHops()) {
				_db.getWritableDatabase().insert("hops", null, ContentValueBuilder.Create()
						.String("form", hop.getFORM())
						.String("name", hop.getNAME())
						.String("notes", hop.getNOTES())
						.String("origin", hop.getORIGIN())
						.String("substitutes", hop.getSUBSTITUTES())
						.String("type", hop.getTYPE())
						.String("use", hop.getUSE())
						.Double("alpha", hop.getALPHA())
						.Double("amount", hop.getAMOUNT())
						.Double("beta", hop.getBETA())
						.Double("caryophyllene", hop.getCARYOPHYLLENE())
						.Double("cohumulone", hop.getCOHUMULONE())
						.Double("hsi", hop.getHSI())
						.Double("humulene", hop.getHUMULENE())
						.Double("myrcene", hop.getMYRCENE())
						.Double("time", hop.getTIME())
						.Double("version", hop.getVERSION())
						.Long("recipe_id", recipeId)
						.getValues());
			}

			for (FERMENTABLE f : recipe.getFERMENTABLES().gettheFermentables()) {
				_db.getWritableDatabase().insert("fermentables", null, ContentValueBuilder.Create()
						.Long("recipe_id", recipeId)
						.Boolean("add_after_boil", getBool(f.getADD_AFTER_BOIL()))
						.Double("coarse_fine_diff", getDouble(f.getCOARSE_FINE_DIFF()))
						.Double("amount", f.getAMOUNT())
						.Double("color", f.getCOLOR())
						.Double("diastic_power", getDouble(f.getDIASTIC_POWER()))
						.Double("ibu_gal_per_lb", f.getIBU_GAL_PER_LB())
						.Double("max_in_batch", f.getMAX_IN_BATCH())
						.Double("moisture", getDouble(f.getMOISTURE()))
						.String("name", f.getNAME())
						.String("notes", f.getNOTES())
						.String("origin", f.getORIGIN())
						.Double("protein", getDouble(f.getPROTEIN()))
						.Boolean("recommended_mash", getBool(f.getRECOMMENDED_MASH()))
						.String("supplier", f.getSUPPLIER())
						.String("type", f.getTYPE())
						.Double("yield", f.getYIELD())
						.Double("version", f.getVERSION())
						.Double("moisture", getDouble(f.getMOISTURE()))
						.getValues());
			}
			
			if (recipe.getMISCS() != null && recipe.getMISCS().gettheMiscs() != null) {
				for (MISC misc : recipe.getMISCS().gettheMiscs()) {
					_db.getWritableDatabase().insert("miscs", null, ContentValueBuilder.Create()
							.Long("recipe_id", recipeId)
							.Boolean("amount_is_weight", getBool(misc.getAMOUNT_IS_WEIGHT()))
							.String("name", misc.getNAME())
							.String("notes", misc.getNOTES())
							.String("type", misc.getTYPE())
							.String("use", misc.getUSE())
							.String("use_for", misc.getUSE_FOR())
							.Double("amount", misc.getAMOUNT())
							.Integer("time", (int)misc.getTIME())
							.Double("version", misc.getVERSION())
							.getValues());
				}
			}
			
			for (YEAST yeast : recipe.getYEASTS().gettheYeasts()) {
				_db.getWritableDatabase().insert("yeasts", null, ContentValueBuilder.Create()
						.Long("recipe_id", recipeId)
						.Boolean("add_to_secondary", getBool(yeast.getADD_TO_SECONDARY()))
						.Boolean("amount_is_weight", getBool(yeast.getAMOUNT_IS_WEIGHT()))
						.String("best_for", yeast.getBEST_FOR())
						.String("flocculation", yeast.getFLOCCULATION())
						.String("form", yeast.getFORM())
						.String("laboratory", yeast.getLABORATORY())
						.String("name", yeast.getNAME())
						.String("notes", yeast.getNOTES())
						.String("product_id", yeast.getPRODUCT_ID())
						.String("type", yeast.getTYPE())
						.Double("amount", yeast.getAMOUNT())
						.Double("attenuation", yeast.getATTENUATION())
						.Double("max_reuse", yeast.getMAX_REUSE())
						.Integer("max_temperature", (int)yeast.getMAX_TEMPERATURE())
						.Integer("times_cultured", (int)yeast.getTIMES_CULTURED())
						.Double("version", yeast.getVERSION())
						.getValues());
			}
			
			_db.getWritableDatabase().insert("styles", null, ContentValueBuilder.Create()
					.Long("recipe_id", recipeId)
					.String("category", recipe.getSTYLE().getCATEGORY())
					.String("category_number", recipe.getSTYLE().getCATEGORY_NUMBER())
					.String("examples", recipe.getSTYLE().getEXAMPLES())
					.String("ingredients", recipe.getSTYLE().getINGREDIENTS())
					.String("name", recipe.getSTYLE().getNAME())
					.String("notes", recipe.getSTYLE().getNOTES())
					.String("profile", recipe.getSTYLE().getPROFILE())
					.String("style_guide", recipe.getSTYLE().getSTYLE_GUIDE())
					.String("style_letter", recipe.getSTYLE().getSTYLE_LETTER())
					.String("type", recipe.getSTYLE().getTYPE())
					.Double("abv_max", recipe.getSTYLE().getABV_MAX())
					.Double("abv_min", recipe.getSTYLE().getABV_MIN())
					.Double("ibu_max", recipe.getSTYLE().getIBU_MAX())
					.Double("ibu_min", recipe.getSTYLE().getIBU_MIN())
					.Double("carb_max", recipe.getSTYLE().getCARB_MAX())
					.Double("carb_min", recipe.getSTYLE().getCARB_MIN())
					.Double("og_max", recipe.getSTYLE().getOG_MAX())
					.Double("og_min", recipe.getSTYLE().getOG_MIN())
					.Double("fg_max", recipe.getSTYLE().getFG_MAX())
					.Double("fg_min", recipe.getSTYLE().getFG_MIN())
					.Double("color_max", recipe.getSTYLE().getCOLOR_MAX())
					.Double("color_min", recipe.getSTYLE().getCOLOR_MIN())
					.Double("version", recipe.getSTYLE().getVERSION())
					.getValues());
			db.setTransactionSuccessful();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}
	
	public RECIPE recipeForName(String name) {
		for(RECIPE r:getRecipes()){
			if (r.getNAME().equals(name)){
				return r;
			}
		}
		return null;
	}
	
	public Recipe getDeepRecipe(String name) {
		if (name.equals("fake")){
			Recipe fakeRecipe = new Recipe();
			fakeRecipe.addStep(new TemperatureStep(50, "\u00B0F", 105, "Raise Temperature to 52\u00B0F",false));
			fakeRecipe.addStep(new TimedStep(1, "Steep for 15 minutes"));
			fakeRecipe.addStep(new TemperatureStep(212, "\u00B0F", 150, "Raise to a boil",false));
			fakeRecipe.addStep(new TimedStep(60, "Boil for 60 minutes", true));
			fakeRecipe.addStep(new ManualRecipeStep("Add Glacier Hops"));
			fakeRecipe.addStep(new TimedStep(15, "Boil for 15 minutes"));
			fakeRecipe.addStep(new ManualRecipeStep("Add Glacier Hops"));
			fakeRecipe.addStep(new TimedStep(15, "Boil for 15 minutes"));
			fakeRecipe.addStep(new ManualRecipeStep("Add Irish Moss"));
			fakeRecipe.addStep(new TimedStep(10, "Boil for 10 minutes"));
			fakeRecipe.addStep(new ManualRecipeStep("Add Cascade Hops"));
			fakeRecipe.addStep(new TimedStep(5, "Boil for 5 minutes"));
			fakeRecipe.addStep(new TemperatureStep(80, "\u00B0F", 212, "Chill Wort", true));
			fakeRecipe.addStep(new ManualRecipeStep("Add Yeast"));
			fakeRecipe.addStep(new ManualRecipeStep("Move to Fermenter"));

			return fakeRecipe;
		}
		
		RECIPE theRecipe = recipeForName(name);

		Recipe toBrew = new Recipe();
		toBrew.setName(name);
		toBrew.addStep(new ManualRecipeStep("(Optional) rehydate irish moss in 1/2c water."));
		toBrew.addStep(new TemperatureStep(80, "\u00B0F", 0, "Heat your water", false)); // TODO: change back to 150, or recipe based
		toBrew.addStep(new TimedStep(1, "Steep grains for 15 minutes")); // TODO change back to 15/30 or recipe based
		toBrew.addStep(new ManualRecipeStep("Add your extracts."));
		toBrew.addStep(new TemperatureStep(90, "\u00B0F", 0, "Raise to a boil",false)); // TODO change back to 212
		
		// get the hops, and the moss/miscs
		
		List<StepPair> pairs = new ArrayList<StepPair>();
		
		toBrew.addStep(new TimedStep(60, "Boil for 60 minutes", true));
		
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
	
	public List<String> getCategories() {
		Cursor c = _db.getReadableDatabase().rawQuery("select distinct category from styles order by category", null);
		
		ArrayList<String> categories = new ArrayList<String>();

		if (c.getCount() == 0)
			return categories;

		c.moveToFirst();
		do {
			categories.add(c.getString(0));
		} while(c.moveToNext());

		return categories;
	}

	public List<RECIPE> recipesForCategory(String category) {
		// TODO: make a viewmodel for this
		Cursor c = _db.getReadableDatabase().rawQuery(
				"select r.name, s.ibu_min, s.ibu_max, s.abv_min, s.abv_max from recipes r join styles s on s.recipe_id = r.id where s.category = ? order by r.name",
				new String[]{category});

		ArrayList<RECIPE> recipes = new ArrayList<RECIPE>();

		if (c.getCount() == 0)
			return recipes;

		c.moveToFirst();
		do {
			RECIPE r = new RECIPE();
			STYLE s = new STYLE();
			r.setSTYLE(s);
			r.setName(c.getString(0));
			s.setIBU_MIN(c.getDouble(1));
			s.setIBU_MAX(c.getDouble(2));
			s.setABV_MIN(c.getDouble(3));
			s.setABV_MAX(c.getDouble(4));
			recipes.add(r);
		} while(c.moveToNext());
		
		return recipes;
	}

	@Override
	public int compare(RECIPE lhs, RECIPE rhs) {
		return lhs.getNAME().compareToIgnoreCase(rhs.getNAME());
	}
}
