package com.example.homebrewnavigator;

import java.io.InputStream;
import datamodel.BeerXmlImporter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {
    public Boolean mBrewing = false;
    private SharedPreferences mSettings;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        getActionBar().setTitle("HomeBrew Navigator");
        
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

    private String getCurrentVersion() {
        try{
        	return this
        			.getPackageManager()
        			.getPackageInfo(this.getPackageName(),  0)
        			.versionName;
        }
        catch (NameNotFoundException e) {
        	Log.w("MAIN", "Could not lookup current application version", e);
        	return "UNKNOWN";
        }
    }

    
    @Override
	protected void onResume() {
		super.onResume();

		processUpgrade();
		
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
    
	private void processUpgrade() {
		final String currentVersion = getCurrentVersion();
		
		new UpgradeHelper(this).Go(
				currentVersion,
				mSettings.getString("app_version", "0.0"),
				"Mmm... beer",
				"Loading up some delicious beer recipes.",
				// force database upgrade
				new Runnable(){
					@Override
					public void run() {
						MyContext.getDb().getReadableDatabase();
						MyContext.getDb().close();
					}
				},
				// import beer xml
				new Runnable(){
					@Override
					public void run() {
						BeerXmlImporter importer = new BeerXmlImporter();
						Context ctx = MyContext.getContext();
						if (!mSettings.getBoolean("imported_recipes", false)) {
							int id = ctx.getResources().getIdentifier("recipes", "raw", ctx.getPackageName());
							InputStream contents = ctx.getResources().openRawResource(id);
							importer.importRecipesFromXml(contents, MyContext.getDb());
							mSettings.edit().putBoolean("imported_recipes", true);
						}
					}
				},
				// update app version in prefs
				new Runnable(){
					@Override
					public void run() {
						SharedPreferences.Editor editor = mSettings.edit();
						editor.putString("app_version", currentVersion);
						editor.commit();
					}
				});
	}
}
