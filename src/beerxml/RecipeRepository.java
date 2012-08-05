package beerxml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;

import com.example.homebrewnavigator.MyContext;

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
