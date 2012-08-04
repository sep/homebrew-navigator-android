package com.example.homebrewnavigator.test;

import com.example.homebrewnavigator.bll.IAlarmManager;

public class MockAlarmManager implements IAlarmManager {

	public Boolean fired = false;
	@Override
	public void schduledTimeStepTimerToFire(int minutes) {
		fired = true;
		
	}

}
