package com.example.homebrewnavigator.bll;

public class RecipeBook {
	
	private IRecipeRepository _recipeRepo;
	
	public RecipeBook(IRecipeRepository recipeRepo){
		_recipeRepo = recipeRepo;
	}
	
	public Recipe GetRecipe(String daName) {
		return _recipeRepo.GetRecipeByName(daName);
	}
}
