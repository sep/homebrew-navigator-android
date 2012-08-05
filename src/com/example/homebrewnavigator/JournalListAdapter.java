package com.example.homebrewnavigator;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.journal_row_layout, parent, false);
		
		TextView batchName = (TextView) rowView.findViewById(R.id.batchName);
		RatingBar batchRating = (RatingBar) rowView.findViewById(R.id.batchRating);
		
		batchName.setText(mBatches.get(position));
		batchRating.setRating(position);
		
		return rowView;
	}
	
	

}
