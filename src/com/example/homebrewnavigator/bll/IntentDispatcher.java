package com.example.homebrewnavigator.bll;

import com.example.homebrewnavigator.MyContext;

import android.content.Intent;

public class IntentDispatcher implements IIntentDispatcher {

	@Override
	public void fireStepCompleteIntent() {
		Intent intent = new Intent("temperatureReached");
		MyContext.getContext().sendBroadcast(intent);		
	}

}
