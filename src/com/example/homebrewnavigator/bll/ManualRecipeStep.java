package com.example.homebrewnavigator.bll;

public class ManualRecipeStep extends RecipeStep {

	public ManualRecipeStep(Object target, String units, Object actualValue,
			String instruction) {
		super(null, null, null, instruction);
	}
	
	public ManualRecipeStep(String instruction){
		super(null, null, null, instruction);
	}

	@Override
	public void execute() {
		//do nothing
	}

}
