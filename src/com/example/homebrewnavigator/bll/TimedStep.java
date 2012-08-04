package com.example.homebrewnavigator.bll;

import java.util.Timer;
import java.util.TimerTask;

public class TimedStep extends RecipeStep<Integer>{
	private IAlarmSetter dispatcher;	
	

	public TimedStep(int t, IAlarmSetter dis) {
		target = t;	
		units = "Minutes";			
		dispatcher = dis;
	}

	@Override
	public void execute() {
		dispatcher.schduledTimeStepTimerToFire(target);	
	}		
}
