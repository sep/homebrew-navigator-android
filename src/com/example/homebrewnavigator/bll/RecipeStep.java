package com.example.homebrewnavigator.bll;

import java.util.GregorianCalendar;

public abstract class RecipeStep<T> {
	protected T target;
	protected String units;
	protected T actualValue;
	protected String instruction;	
	protected Boolean isCompleted = false;
	
	public String getInstruction() {
		return instruction;
	}	
	
	public T getValue(){
		return actualValue;
	}
		
	public abstract Boolean canMovetoNextStep();

	public boolean getIsNotCompleted() {		
		return !isCompleted;
	}
	
	public void setIsCompleted() {		
		isCompleted = true;
	}	
}
