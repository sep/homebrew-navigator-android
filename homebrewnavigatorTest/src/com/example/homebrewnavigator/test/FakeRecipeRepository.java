package com.example.homebrewnavigator.test;


import java.util.ArrayList;
import java.util.List;

import com.example.homebrewnavigator.bll.IRecipeRepository;
import com.example.homebrewnavigator.bll.Recipe;

public class FakeRecipeRepository implements IRecipeRepository{

	private ArrayList<Recipe> seedData;
	public FakeRecipeRepository()
	{
		seedData= new ArrayList<Recipe>();
		Recipe r = new Recipe();
		r.setName("My Special Brew");
		seedData.add(r);
	}
	@Override
	public Recipe GetRecipeByName(String name) {

		for(Recipe rec : seedData)
		{
			if (rec.getName() == name)
				return rec;
		}
		
		return null;
	}

}
