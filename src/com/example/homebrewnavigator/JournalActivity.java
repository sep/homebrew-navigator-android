package com.example.homebrewnavigator;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

public class JournalActivity extends Activity {

	private ListView mJournalListView;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
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
