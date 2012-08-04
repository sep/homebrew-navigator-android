package com.example.homebrewnavigator.bll;

public class TemperatureStep extends RecipeStep<Integer> {

	public TemperatureStep(Integer target, String units, Integer actualValue,
			String instruction) {
		super(target, units, actualValue, instruction);
	}

	public TemperatureStep(Integer target, String instruction) {
		super(target, "\u00B0F", null, instruction);
	}

	@Override
	public Boolean canMovetoNextStep() {
		// TODO Auto-generated method stub
		return null;
	}

}
