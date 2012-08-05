package com.example.homebrewnavigator.bll;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class Recipe {

	private String name;
	private ArrayList<RecipeStep> steps;	
	private int currentIndex = 0;
	
	public Recipe(){
		steps = new ArrayList<RecipeStep>();	
	}
	
	public String getName() {		
		return name;
	}

	public void setName(String daName) {
		name = daName;		
	}
	
	public void addStep(RecipeStep step){
		steps.add(step);
	}
	public RecipeStep getCurrentStep() {
		if (steps.isEmpty() || currentIndex >= steps.size())
			return null;	
			
		return (RecipeStep)steps.get(findNextStepIndex());
	}

	public List<RecipeStep> getNextSteps() {	
		if (findNextStepIndex() == -1)
			return new ArrayList<RecipeStep>();
		
		return steps.subList(findNextStepIndex()+1, steps.size());
	}
	
	private int findNextStepIndex(){		
		for(int i=0; i< steps.size(); i++){
			if (((RecipeStep)steps.get(i)).getIsNotCompleted())
				return i;					
		}		
		return -1;
	}

}
