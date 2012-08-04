package com.example.homebrewnavigator;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
	private Boolean mPaused = false;
	
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
		Object currentValue = currentStep.getValue();
		Object targetValue = currentStep.getTarget();
		
		TextView tvCurrentValue = (TextView)mActivityBrewDay.findViewById(R.id.tvCurrentValue);
		
		if( currentValue != null && currentValue != "" && currentUnits != null && currentUnits != "" ){
			tvCurrentValue.setText(currentValue + " " + currentUnits);			
		}
		else
		{
			tvCurrentValue.setText("");
		}

		TextView tvCurrentInstruction = (TextView)mActivityBrewDay.findViewById(R.id.tvCurrentInstruction);
		tvCurrentInstruction.setText(currentStep.getInstruction());

		TextView tvTimeRemainingValue = (TextView)mActivityBrewDay.findViewById(R.id.tvTimeRemainingValue);
		TextView tvTimeRemainingText = (TextView)mActivityBrewDay.findViewById(R.id.tvTimeRemainingText);

		if( currentUnits != null && currentUnits != "" && targetValue != null && currentValue != null)
		{
		    tvTimeRemainingValue.setText(""+((Integer)targetValue - (Integer)currentValue));
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
    	Button pause = (Button)mActivityBrewDay.findViewById(R.id.bPause);
    	if( mPaused ){
    		// TODO: handle unpausing
    		pause.setText("Pause");
    	}
    	else
    	{
    		// TODO: handle pausing
    		pause.setText("Play");
    	}
    	mPaused = !mPaused;
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
    	if( mRecipe.getNextSteps() != null && mRecipe.getNextSteps().size() > 0 ){
    	    AlertDialog.Builder builder = new AlertDialog.Builder(this); 
    	    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener) 
    	        .setNegativeButton("No", dialogClickListener).show(); 
    	}
    	else
    	{
	        finish();
    	}
    }

	@Override
	public void finish() {
    	// TODO: add logic to mark recipe as done
		super.finish();
	}
    
    
    

	

}

