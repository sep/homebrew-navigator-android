package com.example.homebrewnavigator.recipeManager;

import com.example.homebrewnavigator.BrewDayActivity;
import com.example.homebrewnavigator.MainActivity;
import com.example.homebrewnavigator.MyContext;
import com.example.homebrewnavigator.R;
import com.example.homebrewnavigator.R.id;
import com.example.homebrewnavigator.R.layout;

import datamodel.RecipeRepository;
import datamodel.RecipeViewModel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class RecipeActivity extends Activity {

	RecipeRepository mRepository;
	RecipeViewModel mRecipe;
	
	public RecipeActivity() {
		mRepository = new RecipeRepository(MyContext.getDb());
		
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final ActionBar bar = getActionBar();

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		String recipeName = getIntent().getExtras().getString("recipeName");
		RecipeViewModel recipe = mRepository.recipeForName(recipeName);

		mRecipe = recipe;
		
		bar.setTitle(recipeName);
		
		setContentView(R.layout.activity_recipe);
		
		((TextView)findViewById(R.id.ingredients)).setText(recipe.Ingredients);
		((TextView)findViewById(R.id.instructions)).setText(recipe.Instructions);
		
		((TextView)findViewById(R.id.originalGravity)).setText("Original Gravity: " + recipe.OgMax + " - " + recipe.OgMin);
		((TextView)findViewById(R.id.finalGravity)).setText("Final Gravity: " + recipe.FgMax + " - " + recipe.FgMin);
		
		super.onCreate(savedInstanceState);
	}

    public void handleBrewThis(View view) {
    	startActivity(new Intent().setClass(this, BrewDayActivity.class).putExtra("recipeName", mRecipe.Name));
    }
}
