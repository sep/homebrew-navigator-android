package beerxml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;

import com.example.homebrewnavigator.MyContext;
import com.example.homebrewnavigator.bll.ManualRecipeStep;
import com.example.homebrewnavigator.bll.Recipe;
import com.example.homebrewnavigator.bll.TemperatureStep;
import com.example.homebrewnavigator.bll.TimedStep;

public class RecipeRepository {

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
}
