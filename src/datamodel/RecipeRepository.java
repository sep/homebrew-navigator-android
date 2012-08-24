package datamodel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import utility.CursorHelper;
import utility.Selector;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import beerxml.RECIPE;
import com.example.homebrewnavigator.bll.ManualRecipeStep;
import com.example.homebrewnavigator.bll.Recipe;
import com.example.homebrewnavigator.bll.TemperatureStep;
import com.example.homebrewnavigator.bll.TimedStep;

public class RecipeRepository implements Comparator<RECIPE>{

	public static SQLiteOpenHelper _db;
	
	public RecipeRepository(SQLiteOpenHelper db) {
		_db = db;
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
				"select r.id, r.batch_size, r.boil_size, r.boil_time, r.notes, r.type, s.og_max, s.og_min, s.fg_max, s.fg_min, s.ibu_max, s.ibu_min, s.abv_max, s.abv_min, s.name, s.category from recipes r join styles s on r.id = s.recipe_id where r.name = ?",
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
				r.BoilTime = c.getInt(i++);
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
		RecipeViewModel recipe = recipeForName2(name);

		Recipe toBrew = new Recipe();
		toBrew.setName(name);
		toBrew.addStep(new ManualRecipeStep("(Optional) rehydate irish moss in 1/2c water."));
		toBrew.addStep(new TemperatureStep(150, "\u00B0F", 0, "Heat your water", false));
		toBrew.addStep(new TimedStep(30, "Steep grains for 30 minutes"));
		toBrew.addStep(new ManualRecipeStep("Add your extracts."));
		toBrew.addStep(new TemperatureStep(212, "\u00B0F", 0, "Raise to a boil",false));
		
		List<StepPair> pairs = new ArrayList<StepPair>();
		
		toBrew.addStep(new TimedStep(recipe.BoilTime, "Boil for " + recipe.BoilTime  + " minutes", true));
		
		for(HopTime h : getHopTimes(recipe.Id)) {
			pairs.add(new StepPair("Add " + h.name + " hops", 60-h.time));
		}	
		for(MiscTime m: getMiscTimes(recipe.Id)) {
			pairs.add(new StepPair("Add " + m.name, m.time));
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
		
		toBrew.addStep(new TemperatureStep(70, "\u00B0F", 212, "Chill Wort", true));
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
	
	private class HopTime {
		public String name;
		public int time;
	}
	
	private class MiscTime {
		public String name;
		public int time;
	}
	
	private List<HopTime> getHopTimes(long recipeId) {
		Cursor hc = _db.getReadableDatabase().rawQuery(
				"select name, time from hops where recipe_id = ?",
				new String[]{Long.toString(recipeId)});

		return CursorHelper.loop(hc, new Selector<HopTime, Cursor>() {
			@Override
			public HopTime map(Cursor c) {
				int i = 0;
				HopTime hop = new HopTime();
				hop.name = c.getString(i++);
				hop.time = c.getInt(i++);
				return hop;
			}
		});
	}
	
	private List<MiscTime> getMiscTimes(long recipeId) {
		Cursor hc = _db.getReadableDatabase().rawQuery(
				"select name, time from miscs where recipe_id = ?",
				new String[]{Long.toString(recipeId)});

		return CursorHelper.loop(hc, new Selector<MiscTime, Cursor>() {
			@Override
			public MiscTime map(Cursor c) {
				int i = 0;
				MiscTime misc = new MiscTime();
				misc.name = c.getString(i++);
				misc.time = Math.round(c.getInt(i++));
				return misc;
			}
		});
	}
	
	private String getInstructionsText(long recipeId) {
		StringBuilder builder = new StringBuilder();
		int cnt = 0;

		builder.append(MakeInstruction(++cnt, "(Optional) rehyrdate irish moss in 1/2c water."));
		builder.append(MakeInstruction(++cnt, "Heat your water to 150 (F)."));
		builder.append(MakeInstruction(++cnt, "Place specialty grains in pot."));
		builder.append(MakeInstruction(++cnt, "Steep for 30 minutes"));
		builder.append(MakeInstruction(++cnt, "Boil")); // set flag
		builder.append(MakeInstruction(++cnt, "Add Extract"));
		builder.append(MakeInstruction(++cnt, "Bring to boil 212 (F)"));

		List<StepPair> pairs = new ArrayList<StepPair>();

		for(HopTime h : getHopTimes(recipeId)) {
			pairs.add(new StepPair("Add " + h.name + " hops", 60-h.time));
		}

		for(MiscTime m : getMiscTimes(recipeId)) {
			pairs.add(new StepPair("Add " + m.name, m.time));
		}
		pairs.add(new StepPair("(Optional) Place wort chiller in wort", 50));

		Collections.sort(pairs, new StepComparator());

		int boil = 0;
		for(StepPair p:pairs) {
			double time = p.getValue() - boil;

			builder.append(MakeInstruction(++cnt, p.getText()));
			boil += time;
		}

		builder.append(MakeInstruction(++cnt, "(Optional) add moss at XX min"));
		builder.append(MakeInstruction(++cnt, "(Optional) add wort chiller at 50 min."));
		builder.append(MakeInstruction(++cnt, "chill wort 70 (F)")); //set flag
		builder.append(MakeInstruction(++cnt, "transfer"));
		builder.append(MakeInstruction(++cnt, "pitch yeast"));

		return builder.toString();
	}

	
	private String MakeInstruction(int cnt, String instruction) {
		return cnt + ". " + instruction + "\n";
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

		Cursor mc = _db.getReadableDatabase().rawQuery(
				"select name, amount, amount_is_weight, time from miscs where recipe_id = ?",
				new String[]{Long.toString(recipeId)});

		CursorHelper.loop(mc, new Selector<Void, Cursor>() {
			@Override
			public Void map(Cursor c) {
				int i = 0;
				String name = c.getString(i++);
				double amount = c.getDouble(i++);
				boolean isWeight = c.getInt(i++) == 1;
				int time = c.getInt(i++);
				String timeStr = (time == 0)  ? "" : " at " + time; // TODO: what if the value actually is zero?

				String qty = round(amount * (isWeight ? 2.20462 : 33.814), 2) + "";
				String unit = isWeight ? "lbs" : "oz";
				
				builder.append(qty + " " + unit + " " + name + timeStr + "\n");

				return null;
			}
		});
		
		builder.append("\n");

		Cursor yc = _db.getReadableDatabase().rawQuery(
				"select name, product_id from yeasts where recipe_id = ?",
				new String[]{Long.toString(recipeId)});

		CursorHelper.loop(yc, new Selector<Void, Cursor>() {
			@Override
			public Void map(Cursor c) {
				int i=0;
				builder.append("Yeast: " + c.getString(i++) + " " + c.getString(i++) + "\n");
				return null;
			}
		});

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
