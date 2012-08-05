package com.example.homebrewnavigator;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class JournalActivity extends Activity {

	private ListView mJournalListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_journal);
		
		mJournalListView = (ListView) findViewById(R.id.journalListView);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		List<String> batches = new ArrayList<String>();
		
		batches.add("Kolsch");
		batches.add("Trippel");
		batches.add("Cream Ale");
		
		JournalListAdapter journalList = new JournalListAdapter(getApplicationContext(), batches);
		mJournalListView.setAdapter(journalList);
	}
	
	

}
