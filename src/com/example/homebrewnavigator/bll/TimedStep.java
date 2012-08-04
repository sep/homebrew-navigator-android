package com.example.homebrewnavigator.bll;

public class TimedStep extends RecipeStep<Integer>{
	public TimedStep(int t) {
		target = t;
		units = "Minutes";		
	}

	@Override
	public Boolean canMovetoNextStep() {
		// TODO Auto-generated method stub
		return null;
	}
}
