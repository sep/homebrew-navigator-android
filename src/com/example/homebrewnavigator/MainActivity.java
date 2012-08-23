package com.example.homebrewnavigator;

import java.io.InputStream;

import datamodel.BeerXmlImporter;
import datamodel.RecipeRepository;
import db.DbAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {
    public Boolean mBrewing = false;
    private String currentApplicationVersion = "UNKNOWN";
    private String lastInstalledApplicationVersion = "UNKNOWN";
    private SharedPreferences mSettings;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        getActionBar().setTitle("HomeBrew Navigator");
        
        try{
        	currentApplicationVersion = this
        			.getPackageManager()
        			.getPackageInfo(this.getPackageName(),  0)
        			.versionName;
        }
        catch (NameNotFoundException e) {
        	Log.w("MAIN", "Could not lookup current application version", e);
        }
        
        mSettings = getSharedPreferences(getString(R.string.prefs_name), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	startActivity(new Intent().setClassName(getApplicationContext(), BrewDayActivity.class.getName()));
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();

		lastInstalledApplicationVersion = mSettings.getString("app_version", "0.0");
		if (!lastInstalledApplicationVersion.equals(currentApplicationVersion)) {
			final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "Preparing", "Upgrading application...", true);
			new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... params) {
					MyContext.getDb().getReadableDatabase();
					MyContext.getDb().close();
					
					BeerXmlImporter importer = new BeerXmlImporter();
					Context ctx = MyContext.getContext();
					if (!mSettings.getBoolean("imported_recipes", false)) {
						int id = ctx.getResources().getIdentifier("recipes", "raw", ctx.getPackageName());
						InputStream contents = ctx.getResources().openRawResource(id);
						importer.importRecipesFromXml(contents, MyContext.getDb());
						mSettings.edit().putBoolean("imported_recipes", true);
					}
					mSettings.edit().putString("app_version", currentApplicationVersion);
					
					return null;
				}
				
				@Override
				protected void onPostExecute(Void result) {
					SharedPreferences.Editor editor = mSettings.edit();
					editor.putString("app_version", currentApplicationVersion);
					editor.commit();
					progressDialog.dismiss();
				}
			}.execute();
		}
		
		if( isBrewing() ){
        	startActivity(new Intent().setClassName(getApplicationContext(), BrewDayActivity.class.getName()));
        }
	}
	

	public Boolean isBrewing(){
        Boolean brewing = mSettings.getBoolean(getString(R.string.brewing_pref), false);
        return brewing;
    }

    public void recipeHandler(View v) {
    	startActivity(new Intent().setClassName(getApplicationContext(), RecipeManagerActivity.class.getName()));
    }
    
    public void brewHandler(View v) {
    	startActivity(new Intent().setClassName(getApplicationContext(), BrewDayActivity.class.getName()));
    }
    
    public void thermoHandler(View v) {
    	startActivity(new Intent().setClassName(getApplicationContext(), JournalActivity.class.getName()));
    }
}
