package com.example.homebrewnavigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class JournalEditDetailsActivity extends Activity {

	private EditText mEndGrav;
	private EditText mStartGrav;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_journal_edit_details);
		
		
		
		Intent intent = getIntent();
		if(intent.hasExtra("BatchName")) {
			TextView titleView = (TextView) findViewById(R.id.journalTitleText);
			titleView.setText(intent.getStringExtra("BatchName"));
		}
		
		mEndGrav = (EditText)findViewById(R.id.endGrav);
		mStartGrav = (EditText)findViewById(R.id.initialGrav);
		
		mEndGrav.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(arg1 == false)
				{
					tryCalcAlcoholContent();
				}
			}
			
			
		});
		
		mEndGrav.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				
				if(arg1 == EditorInfo.IME_ACTION_DONE) {
					tryCalcAlcoholContent();
				}
				
				return false;
			}
			
		});
	}
	

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	public void tryCalcAlcoholContent()
	{
		String origGrav = mStartGrav.getText().toString();
		String finalGrav = mEndGrav.getText().toString();
		
		try
		{
			double origGravNum = Double.parseDouble(origGrav);
			double finalGravNum = Double.parseDouble(finalGrav);
			
			double alcContent = (origGravNum - finalGravNum) / 0.00753d;
			
			TextView alcText = (TextView)findViewById(R.id.alcoholContent);
			String alcoholString = Double.toString(alcContent);
			alcoholString = alcoholString.substring(0, alcoholString.indexOf('.')+3) + "%";
			alcText.setText( "Alcohol Content: " + alcoholString);
		}
		catch(Exception e)
		{
			System.out.println("Error calculating stats! " + e.toString());
		}
	}

}