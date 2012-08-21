package com.example.homebrewnavigator;

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

		lastInstalledApplicationVersion = mSettings.getString(getString(R.string.app_version), "0.0");
		if (!lastInstalledApplicationVersion.equals(currentApplicationVersion)) {
			final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "Preparing", "Upgrading application...", true);
			new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... params) {
					new MyDbAdapter(MainActivity.this);
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
	
	private class MyDbAdapter extends SQLiteOpenHelper {
		private static final String DATABASE_NAME = "beer";
		
		public MyDbAdapter(Context context) {
			super(context, DATABASE_NAME, null, Patch.PATCHES.length);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			for (Patch p : Patch.PATCHES)
				p.apply(db);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			for (int i=oldVersion; i<newVersion; i++) {
				Patch.PATCHES[i].apply(db);
			}
		}
		
		@Override
		public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			for (int i=oldVersion; i>newVersion; i++) {
				Patch.PATCHES[i-1].revert(db);
			}
		}
	}
	

	static class Patch {
		public static final Patch[] PATCHES = new Patch[] {
			new Patch() {
				public void apply(SQLiteDatabase db) {
					db.execSQL("create table hops (id INTEGER PRIMARY KEY, recipe_id INTEGER, name TEXT NOT NULL, version DOUBLE NOT NULL, alpha DOUBLE NOT NULL, amount DOUBLE NOT NULL, use TEXT NOT NULL, time DOUBLE NOT NULL, notes TEXT, type TEXT, form TEXT, beta DOUBLE, hsi DOUBLE, origin TEXT, substitutes TEXT, humulene DOUBLE, caryophyllene DOUBLE, cohumulone DOUBLE, myrcene DOUBLE);");
					db.execSQL("create table fermentables (id INTEGER PRIMARY KEY, recipe_id INTEGER, name TEXT NOT NULL, version DOUBLE NOT NULL, type TEXT NOT NULL, amount DOUBLE NOT NULL, yield DOUBLE NOT NULL, color DOUBLE NOT NULL, add_after_boil BOOLEAN, origin TEXT, supplier TEXT, notes TEXT, coarse_fine_diff TEXT, moisture TEXT, diastic_power TEXT, protein TEXT, max_in_batch DOUBLE, recommended_mash BOOLEAN, ibu_gal_per_lb DOUBLE);");
					db.execSQL("create table yeasts (id INTEGER PRIMARY KEY, recipe_id INTEGER, name TEXT NOT NULL, version DOUBLE NOT NULL, type TEXT NOT NULL, form TEXT NOT NULL, amount DOUBLE NOT NULL, amount_is_weight BOOLEAN, laboratory TEXT, product_id TEXT, min_temperature DOUBLE, max_temperature DOUBLE, flocculation TEXT, attenuation DOUBLE, notes TEXT, best_for TEXT, times_cultured DOUBLE, max_reuse DOUBLE, add_to_secondary BOOLEAN);");
					db.execSQL("create table miscs (id INTEGER PRIMARY KEY, recipe_id INTEGER, name TEXT NOT NULL, version DOUBLE NOT NULL, type TEXT NOT NULL, use TEXT NOT NULL, time DOUBLE NOT NULL, amount DOUBLE NOT NULL, amount_is_weight BOOLEAN, use_for TEXT, notes TEXT);");
					db.execSQL("create table styles (id INTEGER PRIMARY KEY, recipe_id INTEGER, name TEXT NOT NULL, version DOUBLE NOT NULL, category TEXT NOT NULL, category_number TEXT NOT NULL, style_letter TEXT NOT NULL, style_guide TEXT NOT NULL, type TEXT NOT NULL, og_min DOUBLE NOT NULL, og_max DOUBLE NOT NULL, fg_min DOUBLE NOT NULL, fg_max DOUBLE NOT NULL, ibu_min DOUBLE NOT NULL, ibu_max DOUBLE NOT NULL, color_min DOUBLE NOT NULL, color_max DOUBLE NOT NULL, carb_min DOUBLE, carb_max DOUBLE, abv_min DOUBLE, abv_max DOUBLE, notes TEXT, profile TEXT, ingredients TEXT, examples TEXT);");
					db.execSQL("create table recipes (id INTEGER PRIMARY KEY, name TEXT NOT NULL, version DOUBLE NOT NULL, type TEXT NOT NULL, brewer TEXT NOT NULL, asst_brewer TEXT, batch_size DOUBLE NOT NULL, boil_size DOUBLE NOT NULL, boil_time DOUBLE NOT NULL, efficiency DOUBLE, notes TEXT, taste_notes TEXT, taste_rating DOUBLE, og DOUBLE, fg DOUBLE, fermentation_stages DOUBLE, primary_age DOUBLE, primary_temp DOUBLE, secondary_age DOUBLE, secondary_temp DOUBLE, tertiary_age DOUBLE, tertiary_temp DOUBLE, age DOUBLE, age_temp DOUBLE, date TEXT, carbonation DOUBLE, forced_carbonation BOOLEAN, priming_sugar_name TEXT, carbonation_temp DOUBLE, priming_sugar_equiv DOUBLE, keg_priming_factor DOUBLE);");
				}
				public void revert(SQLiteDatabase db) {
				    //
				}
			},
		};

		public void apply(SQLiteDatabase db) {}
		public void revert(SQLiteDatabase db) {}
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
