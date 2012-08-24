package com.example.homebrewnavigator;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class UpgradeHelper {

	private Context _context;
	
	public UpgradeHelper(Context context) {
		_context = context;
	}

	public void Go(final String currentVersion, final String previousVersion, String title, String text, final Runnable... toExecute) {
		if (currentVersion.equalsIgnoreCase(previousVersion)) {
			return;
		}
		
		final ProgressDialog progressDialog = ProgressDialog.show(_context, title, text, true);
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				for (Runnable r : toExecute) {
					r.run();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				progressDialog.dismiss();
			}
		}.execute();
	}
}