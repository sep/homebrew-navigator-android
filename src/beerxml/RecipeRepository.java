package beerxml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.ContentValues;
import android.content.Context;
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
					.Boolean("add_after_bool", getBool(f.getADD_AFTER_BOIL()))
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

		// miscs
		// style
		// yeasts
		
		// insert into database
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
		List<RECIPE> recipes = getRecipes();
	
		ArrayList<String> categories = new ArrayList<String>();
		for(RECIPE r:recipes){
			if (!categories.contains(r.getSTYLE().getCATEGORY())) {
				categories.add(r.getSTYLE().getCATEGORY());
			}
		}
			
		return categories;
	}

	public List<RECIPE> recipesForCategory(String category) {
		List<RECIPE> recipes = getRecipes();

		ArrayList<RECIPE> matched = new ArrayList<RECIPE>();
		for(RECIPE r:recipes){
			if (r.getSTYLE().getCATEGORY().equals(category)){
				matched.add(r);
			}
		}
			
		return matched;
	}

	@Override
	public int compare(RECIPE lhs, RECIPE rhs) {
		return lhs.getNAME().compareToIgnoreCase(rhs.getNAME());
	}
}
