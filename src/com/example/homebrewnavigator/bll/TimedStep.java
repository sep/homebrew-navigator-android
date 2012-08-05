package com.example.homebrewnavigator.bll;

public class TimedStep extends RecipeStep<Integer>{
	public TimedStep(Integer target, String units, Integer actualValue,
			String instruction) {
		super(target, units, actualValue, instruction);
	}

	public TimedStep(Integer t, String instruction) {
		super(t, "Minutes", 0, instruction);
	}

	@Override
	public Boolean canMovetoNextStep() {
		// TODO Auto-generated method stub
		return null;
	}
}
