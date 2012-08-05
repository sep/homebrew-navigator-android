package com.example.homebrewnavigator.test;

import com.example.homebrewnavigator.bll.IAlarmSetter;

public class MockAlarmManager implements IAlarmSetter {

	public Boolean fired = false;
	@Override
	public void schduledTimeStepTimerToFire(int minutes) {
		fired = true;
		
	}

}
