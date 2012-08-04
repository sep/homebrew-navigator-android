package com.example.homebrewnavigator;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BrewDayActivity extends Activity {
	private RelativeLayout mActivityBrewDay = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();
        mActivityBrewDay = (RelativeLayout)getLayoutInflater().inflate(R.layout.activity_brew_day,null);
        setContentView(mActivityBrewDay);
    }

	@Override
	protected void onResume() {
		super.onResume();
		
		TextView tvCurrentValue = (TextView)mActivityBrewDay.findViewById(R.id.tvCurrentValue);
		tvCurrentValue.setText("105\u00B0F");

		TextView tvCurrentInstruction = (TextView)mActivityBrewDay.findViewById(R.id.tvCurrentInstruction);
		tvCurrentInstruction.setText("Heat to 150\u00B0F");

		TextView tvTimeRemainingValue = (TextView)mActivityBrewDay.findViewById(R.id.tvTimeRemainingValue);
		tvTimeRemainingValue.setText("60");

		TextView tvTimeRemainingText = (TextView)mActivityBrewDay.findViewById(R.id.tvTimeRemainingText);
		tvTimeRemainingText.setText("minutes remaining");

		TextView tvExpectedEndTime = (TextView)mActivityBrewDay.findViewById(R.id.tvExpectedEndTime);
		tvExpectedEndTime.setText("Done @ 2:00 PM");
		
		String[] fakeUpcomingSteps = getResources().getStringArray(R.array.fakeUpcomingSteps);
		UpcomingStepsAdapter laUpcomingSteps = new UpcomingStepsAdapter(this, fakeUpcomingSteps);
		
		ListView lvUpcomingSteps = (ListView)mActivityBrewDay.findViewById(R.id.lvUpcomingSteps);
		lvUpcomingSteps.setAdapter(laUpcomingSteps);
	}
	
	
	

}

