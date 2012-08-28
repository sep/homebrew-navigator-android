package com.example.homebrewnavigator;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

public class ImportTabFragment extends Fragment {

	// TODO: finish this, make a view, do imports
	
	Context _context;
	Bundle _bundledArguments;
	
	public ImportTabFragment() {
		super();
	}
	
	public static ImportTabFragment newInstance(Bundle bundle, Context context) {
		ImportTabFragment newFrag = new ImportTabFragment();
		newFrag.setContext(context);
		newFrag._bundledArguments = bundle;
		return newFrag;
	}

	public void setContext(Context context) {
		_context = context;
	}
}