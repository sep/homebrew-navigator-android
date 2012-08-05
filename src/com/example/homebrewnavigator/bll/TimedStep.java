package com.example.homebrewnavigator.bll;

import java.util.Timer;
import java.util.TimerTask;

public class TimedStep extends RecipeStep<Integer>{
	private IAlarmSetter dispatcher;
	private Boolean isDispatched = false;
	
	public TimedStep(Integer target, String units, Integer actualValue,
			String instruction) {
		super(target, units, actualValue, instruction);
		dispatcher = new AlarmSetter();
		
	}

	public TimedStep(Integer t, String instruction) {
		super(t, "Minutes", 0, instruction);
		dispatcher = new AlarmSetter();
	}
	public TimedStep(int t, IAlarmSetter dis) {
		super(t, "Minutes", 0, "");
		target = t;	
		units = "Minutes";			
		dispatcher = dis;
		
		dispatcher = new AlarmSetter();
	}

	@Override
	public void execute() {
		if( !isDispatched ){
			dispatcher.schduledTimeStepTimerToFire(target);	
			isDispatched = true;
		}
	}		
}
