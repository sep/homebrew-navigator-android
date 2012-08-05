package com.example.homebrewnavigator.bll;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

	private String name;
	private List<RecipeStep> steps;
	
	public Recipe(){
		steps = new ArrayList<RecipeStep>();
	}
	
	public String getName() {		
		return name;
	}

	public void setName(String daName) {
		name = daName;		
	}
	
	public List<RecipeStep> getSteps(){
		return null;
	}

	public RecipeStep getFirstStep() {
		// TODO Auto-generated method stub
		return null;
	}

	public RecipeStep getNextStep() {
		// TODO Auto-generated method stub
		return null;
	}

}
