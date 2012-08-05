package com.example.homebrewnavigator;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homebrewnavigator.bll.FakeRecipeRepository;
import com.example.homebrewnavigator.bll.Recipe;
import com.example.homebrewnavigator.bll.RecipeStep;

public class BrewDayActivity extends Activity {
	private RelativeLayout mActivityBrewDay = null;
	private Recipe mRecipe = null;
	private Boolean mPaused = false;
	private NotificationManager  notiManager;
	//private BroadcastReceiver temperatureReachedReceiver;
	private BroadcastReceiver updatedTemperatureReceiver;
	private BroadcastReceiver timedStepCompleteReceiver;
	
	
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
					
				}				
			}
		};
		if (timedStepCompleteReceiver!=null) {
			registerReceiver(timedStepCompleteReceiver, new IntentFilter("timedStepComplete"));
		}
		
//		temperatureReachedReceiver = new BroadcastReceiver() {
//
//			@Override
//			public void onReceive(Context context, Intent intent) {				
//				if (intent.getAction() == "temperatureReached") {
//					DoSomethingWithTemp();
//				}
//			}
//		};
//		if (temperatureReachedReceiver!=null) {
//			registerReceiver(temperatureReachedReceiver, new IntentFilter("temperatureReached"));
//		}
		
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
		
		if (rs.getUnits() == "\u00B0F"){
			rs.setValue((int)temp);
			if ( Float.parseFloat(rs.getTarget().toString()) <= temp){
				triggerNotification(temp);				
			}
			updateFields();
		}			
	}
	
	private static final int TEMP_NOTIF_ID = 1;
	
	private void triggerNotification(float temp) {		
			int icon = R.drawable.tempnot;
			Context context = getApplicationContext();
			String contentTitle = "Temperature Step notification";
			String contentText = "Your current temperature is " + temp;
			Notification.Builder builder = new Notification.Builder(context)
	        .setContentTitle(contentTitle)
	        .setContentText(contentText)
	        .setSmallIcon(icon);
			
			Notification noti = builder.getNotification();
			
			notiManager.notify(TEMP_NOTIF_ID+1, noti);				
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
		
		tvCurrentValue = (TextView) mActivityBrewDay.findViewById(R.id.tvCurrentValue);
		
		if (currentUnits.equalsIgnoreCase("minutes")) {
			startCountDownTimer(convertMinutesToTimeInMillis(targetValue));
		}
			

		
//
//		if (currentValue != null && currentValue != "" && currentUnits != null
//				&& currentUnits != "") {
//			tvCurrentValue.setText(currentValue + " " + currentUnits);
//		} else {
//			tvCurrentValue.setText("");
//		}

		TextView tvCurrentInstruction = (TextView) mActivityBrewDay
				.findViewById(R.id.tvCurrentInstruction);
		tvCurrentInstruction.setText(currentStep.getInstruction());

		TextView tvTimeRemainingValue = (TextView) mActivityBrewDay
				.findViewById(R.id.tvTimeRemainingValue);
		TextView tvTimeRemainingText = (TextView) mActivityBrewDay
				.findViewById(R.id.tvTimeRemainingText);

		if (currentUnits != null && currentUnits != "" && targetValue != null
				&& currentValue != null) {
			tvTimeRemainingValue.setText(""
					+ ((Integer) targetValue - (Integer) currentValue));
			tvTimeRemainingText.setText(currentUnits + " remaining");
		} else {
			tvTimeRemainingValue.setText("");
			tvTimeRemainingText.setText("");
		}

		TextView tvExpectedEndTime = (TextView) mActivityBrewDay
				.findViewById(R.id.tvExpectedEndTime);
		// tvExpectedEndTime.setText("Done @ 2:00 PM");

		List<RecipeStep> nextSteps = mRecipe.getNextSteps();
		ListView lvUpcomingSteps = (ListView) mActivityBrewDay
				.findViewById(R.id.lvUpcomingSteps);

		UpcomingStepsAdapter laUpcomingSteps = new UpcomingStepsAdapter(this,
				mRecipe.getNextSteps());

		lvUpcomingSteps.setAdapter(laUpcomingSteps);

		if (nextSteps == null || nextSteps.size() == 0) {
			Button next = (Button) mActivityBrewDay
					.findViewById(R.id.bNextStep);
			next.setEnabled(false);
			lvUpcomingSteps.setVisibility(0);
		}

	}

	private long convertMinutesToTimeInMillis(Object targetValue) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.roll(Calendar.MINUTE, ((Integer)targetValue).intValue());
		return gc.getTimeInMillis();
	}

	private void startCountDownTimer(long timeInMillis) {
		
		new CountDownTimer(timeInMillis, 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				
				//MM:SS
				int remaining = (int) (millisUntilFinished/1000);
				StringBuilder sb = new StringBuilder().append(remaining);
				tvCurrentValue.setText(sb.toString());
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				tvCurrentValue.setText("00:00");
			}
		}.start();
	}

	public void nextHandler(View v) {
		RecipeStep currentStep = mRecipe.getCurrentStep();
		if (currentStep != null) {
			mRecipe.getCurrentStep().setIsCompleted();
		}

		updateFields();
	}

	public void pauseHandler(View v) {
		Button pause = (Button) mActivityBrewDay.findViewById(R.id.bPause);
		if (mPaused) {
			// TODO: handle unpausing
			pause.setText("Pause");
		} else {
			// TODO: handle pausing
			pause.setText("Play");
		}
		mPaused = !mPaused;
	}

	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				// Yes button clicked
				finish();
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				// No button clicked
				break;
			}
		}
	};
	private TextView tvCurrentValue;

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
		super.finish();
//	 	unregisterReceiver(temperatureReachedReceiver);
	 	unregisterReceiver(updatedTemperatureReceiver);
	 	unregisterReceiver(timedStepCompleteReceiver);		
	}

}
