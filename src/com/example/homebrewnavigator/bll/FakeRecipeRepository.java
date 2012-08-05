package com.example.homebrewnavigator.bll;

public class FakeRecipeRepository implements IRecipeRepository {

	public Recipe GetRecipeByName(String name) {
		Recipe fakeRecipe = new Recipe();
		fakeRecipe.addStep(new TemperatureStep(150, "\u00B0F", 105, "Raise Temperature to 150\u00B0F"));
		fakeRecipe.addStep(new TimedStep(15, "Steep for 15 minutes"));
		fakeRecipe.addStep(new TemperatureStep(212, "\u00B0F", 150, "Raise to a boil"));
		fakeRecipe.addStep(new TimedStep(15, "Boil for 15 minutes"));
		fakeRecipe.addStep(new ManualRecipeStep("Add Glacier Hops"));
		fakeRecipe.addStep(new TimedStep(15, "Boil for 15 minutes"));
		fakeRecipe.addStep(new ManualRecipeStep("Add Glacier Hops"));
		fakeRecipe.addStep(new TimedStep(15, "Boil for 15 minutes"));
		fakeRecipe.addStep(new ManualRecipeStep("Add Irish Moss"));
		fakeRecipe.addStep(new TimedStep(10, "Boil for 10 minutes"));
		fakeRecipe.addStep(new ManualRecipeStep("Add Cascade Hops"));
		fakeRecipe.addStep(new TimedStep(5, "Boil for 5 minutes"));
		fakeRecipe.addStep(new ManualRecipeStep("Add Yeast"));
		fakeRecipe.addStep(new TemperatureStep(80, "\u00B0F", 212, "Chill Wort"));
		fakeRecipe.addStep(new ManualRecipeStep("Move to Fermenter"));
		return fakeRecipe;
	}

}
