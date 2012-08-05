package com.example.homebrewnavigator;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class JournalListAdapter extends ArrayAdapter<String> {
	
	private List<String> mBatches;
	private Context mContext;

	public JournalListAdapter(Context context, List<String> batches){
		super(context, R.layout.journal_row_layout, batches);
		
		mBatches = batches;
		mContext = context;
	}
	
	private OnClickListener mEditDetailsListener = new OnClickListener() {
		
		public void onClick(View v) {
			TextView target = (TextView)v;
			
			String contents = target.getText().toString();
						
			Intent intent = new Intent();
			intent.setClassName(MyContext.getContext(), JournalEditDetailsActivity.class.getName());
			intent.putExtra("BatchName", contents);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			MyContext.getContext().startActivity(intent);
		}
	};

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.journal_row_layout, parent, false);
		
		TextView batchName = (TextView) rowView.findViewById(R.id.batchName);
		RatingBar batchRating = (RatingBar) rowView.findViewById(R.id.batchRating);
		
		batchName.setText(mBatches.get(position));
		batchRating.setRating(position);
		
		batchName.setOnClickListener(mEditDetailsListener);
		
		return rowView;
	}
	
	
	
		
	
	

}
