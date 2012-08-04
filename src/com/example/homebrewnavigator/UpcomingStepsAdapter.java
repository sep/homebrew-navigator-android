package com.example.homebrewnavigator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UpcomingStepsAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
	
	public UpcomingStepsAdapter(Context context, String[] values){
		super(context, android.R.layout.simple_list_item_1, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
		TextView textView = (TextView) rowView.findViewById(android.R.id.text1);
		textView.setText(values[position]);
		
		return rowView;
	}

}
