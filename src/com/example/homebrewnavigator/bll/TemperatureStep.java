package com.example.homebrewnavigator.bll;

public class TemperatureStep extends RecipeStep<Integer> {

	private IIntentDispatcher dispatcher; 
	public TemperatureStep(int i, IIntentDispatcher d) {
		dispatcher = d;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub		
	}

}
