package com.example.homebrewnavigator.recipeManager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.homebrewnavigator.R;

public class ImportTabFragment extends Fragment {

	// TODO: finish this, make a view, do imports
	
	private static final int PICKFILE_RESULT_CODE = 1;
	
	Context _context;
	Bundle _bundledArguments;
	TextView _txtFilename;
	
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

    void handleImport() {
    	Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    	intent.setType("file/*");
    	startActivityForResult(intent, PICKFILE_RESULT_CODE);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.import_tab, container, false);
		
		_txtFilename = (TextView)v.findViewById(R.id.txtFilename);
		Button b = (Button)v.findViewById(R.id.btnImport);
		b.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) { handleImport();}
		});
		
		
		return v;
    }
    
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch(requestCode) {
    	case PICKFILE_RESULT_CODE:
    		if (resultCode == Activity.RESULT_OK) {
    			String path = data.getData().getPath();
    			_txtFilename.setText(path);
    		}
    	}
    }
}