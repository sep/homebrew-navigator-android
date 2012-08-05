package com.example.homebrewnavigator.bll;

import java.util.Timer;
import java.util.TimerTask;

public class TimedStep extends RecipeStep<Integer>{
	private IAlarmSetter dispatcher;	
	
	public TimedStep(Integer target, String units, Integer actualValue,
			String instruction) {
		super(target, units, actualValue, instruction);
	}

	public TimedStep(Integer t, String instruction) {
		super(t, "Minutes", 0, instruction);
	}
	public TimedStep(int t, IAlarmSetter dis) {
		super(t, "Minutes", 0, "");
		target = t;	
		units = "Minutes";			
		dispatcher = dis;
	}

	@Override
	public void execute() {
		dispatcher.schduledTimeStepTimerToFire(target);	
	}		
}
