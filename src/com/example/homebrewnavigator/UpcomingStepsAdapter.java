package com.example.homebrewnavigator;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.homebrewnavigator.bll.RecipeStep;

public class UpcomingStepsAdapter extends ArrayAdapter<RecipeStep> {
	private final Context context;
	private final String[] values;
	
	public UpcomingStepsAdapter(Context context, List<RecipeStep> steps){
		super(context, android.R.layout.simple_list_item_1, steps);
		
		String[] temp = new String[steps.size()];
		for( int i = 0; i < steps.size(); i++ ) {
			temp[i] = steps.get(i).getInstruction();
		}

		this.context = context;
		this.values = temp;
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
