package com.example.homebrewnavigator.bll;

import java.util.Timer;
import java.util.TimerTask;

public class TimedStep extends RecipeStep<Integer>{
	private IAlarmManager dispatcher;	
	

	public TimedStep(int t, IAlarmManager dis, I) {
		target = t;	
		units = "Minutes";			
		dispatcher = dis;
	}

	@Override
	public void execute() {
		dispatcher.schduledTimeStepTimerToFire(target);	
	}		
}
