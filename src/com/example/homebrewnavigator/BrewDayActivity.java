package com.example.homebrewnavigator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.homebrewnavigator.bll.FakeRecipeRepository;
import com.example.homebrewnavigator.bll.Recipe;
import com.example.homebrewnavigator.bll.RecipeStep;

public class BrewDayActivity extends Activity {
    private static final String TIME_UNITS = "minutes";
	private static final String TEMPERATURE_UNITS = "\u00B0F";
	private static final int TEMP_NOTIF_ID = 1;
	
	private RelativeLayout mActivityBrewDay = null;
	private Recipe mRecipe = null;
	private Boolean mTimesUp = false;
	private NotificationManager  notiManager;
	private RingtoneManager ringToneManager;
	private BroadcastReceiver updatedTemperatureReceiver;
	private BroadcastReceiver timedStepCompleteReceiver;
	private TextView tvCurrentValue;
	private TextView tvTimeRemainingValue;
	private CountDownTimer mCountDownTimer;
	private CountDownTimer mOverallTimer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Resources res = getResources();
		mActivityBrewDay = (RelativeLayout) getLayoutInflater().inflate(
				R.layout.activity_brew_day, null);
		setContentView(mActivityBrewDay);
		timedStepCompleteReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction() == "timedStepComplete") {					
					triggerTendBrewNotification();									
				}				
			}
		};
		if (timedStepCompleteReceiver!=null) {
			registerReceiver(timedStepCompleteReceiver, new IntentFilter("timedStepComplete"));
		}
		
		updatedTemperatureReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction() == "updatedTemperature") {
					float temp = intent.getExtras().getFloat("temp");
					HandleTempUpdate(temp);
				}				
			}

		};
		if (updatedTemperatureReceiver!=null) {
			registerReceiver(updatedTemperatureReceiver, new IntentFilter("updatedTemperature"));
		}
		
		  String ns = Context.NOTIFICATION_SERVICE;
	        notiManager = (NotificationManager) getSystemService(ns);
	        
	        
	       
	}
	
	private void HandleTempUpdate(float temp) {
		RecipeStep rs = mRecipe.getCurrentStep();
		
		if (rs.getUnits() == TEMPERATURE_UNITS){
			rs.setValue((int)temp);
			if ( Float.parseFloat(rs.getTarget().toString()) <= temp){
				triggerTendBrewNotification();				
			}
			updateFields();
		}			
	}
	
	private void triggerTendBrewNotification() {
		if (isBrewing()){
			int icon = R.drawable.notification;
			Context context = getApplicationContext();
			String contentTitle = "Step Complete Notification";	
			
			Intent notificationIntent = new Intent().setClassName(context, BrewDayActivity.class.getName());
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			
			Notification.Builder builder = new Notification.Builder(context)
	        .setContentTitle(contentTitle)
	        .setContentText("Please tend to your brew!")
	        .setSmallIcon(icon)
	        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
	        .setContentIntent(pendingIntent);
			
			Notification noti = builder.getNotification();
			
			notiManager.notify(TEMP_NOTIF_ID+1, noti);	
		}			
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		mRecipe = (new FakeRecipeRepository()).GetRecipeByName("fake");

		updateFields();
	}

	protected void updateFields() {
		RecipeStep currentStep = mRecipe.getCurrentStep();
		String currentUnits = currentStep.getUnits();
		Object currentValue = currentStep.getValue();
		Object targetValue = currentStep.getTarget();		
		Boolean startBoilStep = currentStep.getIsBoilStarter();
		
		tvCurrentValue = (TextView) mActivityBrewDay.findViewById(R.id.tvCurrentValue);
		tvTimeRemainingValue = (TextView) mActivityBrewDay.findViewById(R.id.tvTimeRemainingValue);
		TextView tvTimeRemainingText = (TextView) mActivityBrewDay.findViewById(R.id.tvTimeRemainingText);		
		
		if (currentUnits != null && currentUnits == TEMPERATURE_UNITS && targetValue != null
				&& currentValue != null) {
			tvTimeRemainingValue.setText(""
					+ ((Integer) targetValue - (Integer) currentValue));
			tvTimeRemainingText.setText(currentUnits + " remaining");
		} else {
			if( mOverallTimer != null ){
				tvTimeRemainingText.setText("left in boil");
			}
			else{
				tvTimeRemainingValue.setText("");
				tvTimeRemainingText.setText("");
			}
		}
		
		if (mCountDownTimer != null) {
			//have to cancel the timer if we get here, otherwise it overwrites the TextView
			mCountDownTimer.cancel();
		}
		
	
		if (currentUnits != null && currentUnits.equalsIgnoreCase(TIME_UNITS)) {
			if (startBoilStep){
				startBoilCountDownTimer(convertMinutesToMillis(targetValue));				
			}
			else{
				startCountDownTimer(convertMinutesToMillis(targetValue));
			}
		} else if (currentValue != null && currentValue != "" && currentUnits != null
				&& currentUnits != "") {
			tvCurrentValue.setText(currentValue + " " + currentUnits);
		} else {
			tvCurrentValue.setText("");
		}
		

		TextView tvCurrentInstruction = (TextView) mActivityBrewDay
				.findViewById(R.id.tvCurrentInstruction);
		tvCurrentInstruction.setText(currentStep.getInstruction());		

		List<RecipeStep> nextSteps = mRecipe.getNextSteps();
		ListView lvUpcomingSteps = (ListView) mActivityBrewDay
				.findViewById(R.id.lvUpcomingSteps);

		UpcomingStepsAdapter laUpcomingSteps = new UpcomingStepsAdapter(this,
				mRecipe.getNextSteps());

		lvUpcomingSteps.setAdapter(laUpcomingSteps);
		
		Button next = (Button) mActivityBrewDay.findViewById(R.id.bNextStep);

		if (nextSteps == null || nextSteps.size() == 0) {
			
			next.setEnabled(false);
			lvUpcomingSteps.setVisibility(0);
		}
	}

	private long convertMinutesToMillis(Object targetValue) {
		//assumes that this Integer is in minutes
		return ((Integer)targetValue).intValue() * 60 * 1000;
	}

	private void startCountDownTimer(long timeInMillis) {
		
		mCountDownTimer = new CountDownTimer(timeInMillis, 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				//MM:SS
				String formattedTime = formatMillisToMinutesAndSeconds(millisUntilFinished);
				tvCurrentValue.setText(formattedTime);
			}
			
			@Override
			public void onFinish() {
				tvCurrentValue.setText("--:--");
			}
		};
		
		mCountDownTimer.start();
	}
	
	private void startBoilCountDownTimer(long timeInMillis) {
		
		if (mOverallTimer == null){
			tvCurrentValue.setText("");
			mOverallTimer = new CountDownTimer(timeInMillis, 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				//MM:SS
				String formattedTime = formatMillisToMinutesAndSeconds(millisUntilFinished);
				tvTimeRemainingValue.setText(formattedTime);
							
			}
			
			@Override
			public void onFinish() {
				tvTimeRemainingValue.setText("--:--");
			}
		};
		
		
			}
	}
	
	private String formatMillisToMinutesAndSeconds(
			long millisUntilFinished) {
		DateFormat timeFormat = new SimpleDateFormat("mm:ss");
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(millisUntilFinished);
		String formattedTime = timeFormat.format(gc.getTime());
		return formattedTime;
	}

	public void nextHandler(View v) {
		if( !isBrewing() ){
	    	Button next = (Button)mActivityBrewDay.findViewById(R.id.bNextStep);
	    	next.setText("Next Step");
	    	startBrewing();
		}
		else
		{
			RecipeStep currentStep = mRecipe.getCurrentStep();
			if (currentStep != null) {
				mRecipe.getCurrentStep().setIsCompleted();
				ClearStepNotifications();	
				
				if (currentStep.getIsBoilStarter()){ //starting the boil kicks off at the end of the step
					mOverallTimer.start();
				}
				if( mRecipe.getCurrentStep().getIsBoilEnder()){ //stopping the boil happens at the start of the next step
					if( mOverallTimer != null){
						mOverallTimer.cancel();
						mOverallTimer = null;
					}
				}
			}
		}
		updateFields();
    }
    
    private void ClearStepNotifications() {
    	notiManager.cancel(TEMP_NOTIF_ID+1);
	}

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() { 
        @Override 
        public void onClick(DialogInterface dialog, int which) { 
            switch (which){ 
            case DialogInterface.BUTTON_POSITIVE: 
                //Yes button clicked 
            	finish();
                break; 
     
            case DialogInterface.BUTTON_NEGATIVE: 
                //No button clicked 
                break; 
            } 
        } 
    }; 
 
	public void doneHandler(View v) {
		if (mRecipe.getNextSteps() != null && mRecipe.getNextSteps().size() > 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure?")
					.setPositiveButton("Yes", dialogClickListener)
					.setNegativeButton("No", dialogClickListener).show();
		} else {
			finish();
		}
	}

	@Override
	public void finish() {
		// TODO: add logic to mark recipe as done
	 	unregisterReceiver(updatedTemperatureReceiver);
	 	unregisterReceiver(timedStepCompleteReceiver);
	 	stopBrewing();
		super.finish();
//	 	unregisterReceiver(temperatureReachedReceiver);		
	}

	@Override
	public void onBackPressed() {
		// if we've started, purposely do nothing
		if( !isBrewing() )
		{
			super.onBackPressed();
		}
	}
    
    public void startBrewing(){
        SharedPreferences settings = getSharedPreferences(getString(R.string.prefs_name), 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(getString(R.string.brewing_pref), true);
        editor.commit();
    }
    
    public void stopBrewing(){
        SharedPreferences settings = getSharedPreferences(getString(R.string.prefs_name), 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(getString(R.string.brewing_pref), false);
        editor.commit();
    }
    
    public Boolean isBrewing(){
        SharedPreferences settings = getSharedPreferences(getString(R.string.prefs_name), 0);
        Boolean brewing = settings.getBoolean(getString(R.string.brewing_pref), false);
        return brewing;
    }

}
