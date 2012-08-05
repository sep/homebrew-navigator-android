package com.example.homebrewnavigator;

import java.text.DecimalFormat;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import beerxml.FERMENTABLE;
import beerxml.HOP;
import beerxml.MISC;
import beerxml.RECIPE;
import beerxml.RecipeRepository;
import beerxml.YEAST;

public class RecipeActivity extends Activity {

	RecipeRepository mRepository;
	RECIPE mRecipe;
	
	public RecipeActivity() {
		mRepository = new RecipeRepository();
		
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
		RECIPE recipe = mRepository.recipeForName(recipeName);

		mRecipe = recipe;
		
		bar.setTitle(recipeName);
		
		
		setContentView(R.layout.activity_recipe);
		// TODO Auto-generated method stub
		
		
		String ingredients = getIngredientsText(recipe);
		((TextView)findViewById(R.id.ingredients)).setText(ingredients);

		String instructions = getInstructionsText(recipe);
		((TextView)findViewById(R.id.instructions)).setText(instructions);
		
		
		((TextView)findViewById(R.id.originalGravity)).setText("Original Gravity: " + recipe.getSTYLE().getOG_MIN() + " - " + recipe.getSTYLE().getOG_MAX());
		((TextView)findViewById(R.id.finalGravity)).setText("Final Gravity: " + recipe.getSTYLE().getFG_MIN() + " - " + recipe.getSTYLE().getFG_MAX());
		
		
		super.onCreate(savedInstanceState);
	}
	
	String getIngredientsText(RECIPE recipe) {

		String ingredients = "";
		for(HOP h:recipe.getHOPS().gettheHops()) {
			ingredients += (round(h.getAMOUNT()*35.274, 2)) + "oz. " + h.getNAME() + " at " + Math.round(h.getTIME()) + "\n";
		}
		
		for(FERMENTABLE f:recipe.getFERMENTABLES().gettheFermentables())
			ingredients += round(f.getAMOUNT()*2.20462, 2) + "lbs " + f.getNAME() + "\n"; 
		
		if (recipe.getMISCS().gettheMiscs() != null) {
			for(MISC m:recipe.getMISCS().gettheMiscs()){
				Boolean isWeight = m.getAMOUNT_IS_WEIGHT().equals("TRUE");
				String qty = round(m.getAMOUNT() * (isWeight ? 2.20462 : 33.814), 2) + "";
				String unit = isWeight ? "lbs" : "oz";
				String time = (m.getTIME() == 0)  ? "" : " at " + m.getTIME(); // TODO: what if the value actually is zero?
				
				ingredients += qty + " " + unit + " " + m.getNAME() + time + "\n";
			}
		}
		
		ingredients += "\n";
		
		for(YEAST y:recipe.getYEASTS().gettheYeasts()) {
			ingredients += "Yeast: " + y.getNAME() + " " + y.getPRODUCT_ID() + "\n";
		}
		
		return ingredients;
	}
	
	String round(double d, int numDecimals) {
        DecimalFormat twoDForm = new DecimalFormat("#." + makeString("#", numDecimals));
        return twoDForm.format(d);
	}
        
    String makeString(String str, int num) {
    	String retValue = "";
    	for(int i=0; i<num; i++)
    		retValue += str;
    	return retValue;
    }
    
    public void handleBrewThis(View view) {
    	startActivity(new Intent().setClass(this, BrewDayActivity.class).putExtra("recipeName", mRecipe.getNAME()));
    }

	String getInstructionsText(RECIPE recipe) {
		String instructions = "";
		
		int cnt = 0;
		
		instructions += MakeInstruction(++cnt, "(Optional) rehyrdate irish moss in 1/2c water.");
		instructions += MakeInstruction(++cnt, "Heat your water to 150 (F).");
		instructions += MakeInstruction(++cnt, "Place specialty grains in pot.");
		instructions += MakeInstruction(++cnt, "Steep for 30 minutes");
		instructions += MakeInstruction(++cnt, "Boil"); // set flag
		instructions += MakeInstruction(++cnt, "Add Extract");
		instructions += MakeInstruction(++cnt, "Bring to boil 212 (F)");
		instructions += MakeInstruction(++cnt, "<add hops in order>");
		instructions += MakeInstruction(++cnt, "(Optional) add moss at XX min");
		instructions += MakeInstruction(++cnt, "(Optional) add wort chiller at 50 min.");
		instructions += MakeInstruction(++cnt, "chill wort 70 (F)"); //set flag
		instructions += MakeInstruction(++cnt, "transfer");
		instructions += MakeInstruction(++cnt, "pitch yeast");
		
		// if you have moss, you should rehydrate it now in 1/2 cup water
		// heat your water to 150
		// place your grain in der pot
		// steep for 30 minutes
		// bring to boil (temp)
		// add extract
		// add hops, in order
		// add moss at right time
		// add wort chiller (if youre doing that)
		// chill to temp (70)
		// transfer
		// pitch yeast

		return instructions;
	}
	
	String MakeInstruction(int cnt, String instruction) {
		return cnt + ". " + instruction + "\n";
	}

}
