package beerxml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;

import com.example.homebrewnavigator.MyContext;

public class RecipeRepository {

	private List<RECIPE> getRecipes() {
		try{
			Context ctx = MyContext.getContext();
			int id = ctx.getResources().getIdentifier("recipes", "raw",
					ctx.getPackageName());
			InputStream contents = ctx.getResources().openRawResource(id);
			Serializer serializer = new Persister();
		
			return serializer.read(RECIPES.class, contents).gettheRecipes();
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return null;
	}

	public List<String> getCategories() {
		List<RECIPE> recipes = getRecipes();
	
		ArrayList<String> categories = new ArrayList<String>();
		for(RECIPE r:recipes){
			if (!categories.contains(r.getSTYLE().getNAME())) {
				categories.add(r.getSTYLE().getNAME());
			}
		}
			
		return categories;
	}

	public List<RECIPE> recipesForCategory(String category) {
		List<RECIPE> recipes = getRecipes();

		ArrayList<RECIPE> matched = new ArrayList<RECIPE>();
		for(RECIPE r:recipes){
			if (r.getSTYLE().getCATEGORY().toString() == category){
				matched.add(r);
			}
		}
			
		return matched;
	}
}
