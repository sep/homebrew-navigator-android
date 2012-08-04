package com.example.homebrewnavigator.bll;

import com.example.homebrewnavigator.MyContext;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmSetter implements IAlarmSetter {

	@Override
	public void schduledTimeStepTimerToFire(int minutes) {
		Context context = MyContext.getContext();
		AlarmManager alarm = (AlarmManager)MyContext.getContext().getSystemService(context.ALARM_SERVICE);
		long milliseconds = System.currentTimeMillis() + minutes * 60 * 1000;
		Intent i= new Intent("timedStepComplete");
		PendingIntent pendingIntent=PendingIntent.getBroadcast(context, 0, i, 0);
		alarm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, milliseconds, pendingIntent);		
	}
}
