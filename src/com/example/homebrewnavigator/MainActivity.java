package com.example.homebrewnavigator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
    public Boolean mBrewing = false;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if( isBrewing() ){
        	startActivity(new Intent().setClassName(getApplicationContext(), BrewDayActivity.class.getName()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public Boolean isBrewing(){
        SharedPreferences settings = getSharedPreferences(getString(R.string.prefs_name), 0);
        Boolean brewing = settings.getBoolean(getString(R.string.brewing_pref), false);
        return brewing;
    }

    public void recipeHandler(View v) {
    	startActivity(new Intent().setClassName(getApplicationContext(), RecipeManagerActivity.class.getName()));
    }
    
    public void brewHandler(View v) {
    	startActivity(new Intent().setClassName(getApplicationContext(), BrewDayActivity.class.getName()));
    }
    
    public void thermoHandler(View v) {
    	startActivity(new Intent().setClassName(getApplicationContext(), ThermometerActivity.class.getName()));
    }
}
