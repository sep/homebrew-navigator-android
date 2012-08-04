package com.example.homebrewnavigator;

import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.homebrewnavigator.bll.FakeRecipeRepository;
import com.example.homebrewnavigator.bll.Recipe;
import com.example.homebrewnavigator.bll.RecipeStep;

public class BrewDayActivity extends Activity {
	private RelativeLayout mActivityBrewDay = null;
	private Recipe mRecipe = null;
	
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
		
		mRecipe = (new FakeRecipeRepository()).GetRecipeByName("fake");
		
		updateFields();
	}
	
	protected void updateFields() {
		RecipeStep currentStep = mRecipe.getCurrentStep();
		String currentUnits = currentStep.getUnits();
		
		TextView tvCurrentValue = (TextView)mActivityBrewDay.findViewById(R.id.tvCurrentValue);
		tvCurrentValue.setText(currentStep.getValue() + " " + currentStep.getUnits());

		TextView tvCurrentInstruction = (TextView)mActivityBrewDay.findViewById(R.id.tvCurrentInstruction);
		tvCurrentInstruction.setText(currentStep.getInstruction());

		TextView tvTimeRemainingValue = (TextView)mActivityBrewDay.findViewById(R.id.tvTimeRemainingValue);
		TextView tvTimeRemainingText = (TextView)mActivityBrewDay.findViewById(R.id.tvTimeRemainingText);

		if( currentUnits != null && currentUnits != "" && currentStep.getTarget() != null && currentStep.getValue() != null)
		{
		    tvTimeRemainingValue.setText(""+((Integer)currentStep.getTarget() - (Integer)currentStep.getValue()));
			tvTimeRemainingText.setText(currentUnits + " remaining");
		}
		else
		{
		    tvTimeRemainingValue.setText("");
			tvTimeRemainingText.setText("");
		}

		TextView tvExpectedEndTime = (TextView)mActivityBrewDay.findViewById(R.id.tvExpectedEndTime);
		//tvExpectedEndTime.setText("Done @ 2:00 PM");
		
		List<RecipeStep> nextSteps = mRecipe.getNextSteps();
	    ListView lvUpcomingSteps = (ListView)mActivityBrewDay.findViewById(R.id.lvUpcomingSteps);

        UpcomingStepsAdapter laUpcomingSteps = new UpcomingStepsAdapter(this, mRecipe.getNextSteps());
		
	    lvUpcomingSteps.setAdapter(laUpcomingSteps);

		if( nextSteps == null || nextSteps.size() == 0 )
		{
	    	Button next = (Button)mActivityBrewDay.findViewById(R.id.bNextStep);
	    	next.setEnabled(false);
			lvUpcomingSteps.setVisibility(0);
		}

	}
	
    public void nextHandler(View v) {
    	RecipeStep currentStep = mRecipe.getCurrentStep();
    	if( currentStep != null ){
        	mRecipe.getCurrentStep().setIsCompleted();    		
    	}
    	
		updateFields();
    }
    
    public void pauseHandler(View v) {
    	// TODO: add logic to pause / unpause current timer
    }
    
    public void doneHandler(View v) {
    	// TODO: add logic to mark recipe as done
    }
    

	

}

