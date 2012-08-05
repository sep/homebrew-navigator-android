package com.example.homebrewnavigator.test;

import com.example.homebrewnavigator.bll.*;

public class MockIntentDispatcher implements IIntentDispatcher {

	public Boolean fireWasCalled = false;
	@Override
	public void fireStepCompleteIntent() {
		fireWasCalled = true;		
	}	

}
