package com.example.homebrewnavigator.bll;

import java.util.GregorianCalendar;

public abstract class RecipeStep<T> {
	protected T target;
	protected String units;
	protected T actualValue;
	protected String instruction;	
	protected Boolean isCompleted = false;
	
	public RecipeStep(T target, String units, T actualValue, String instruction){
		this.target = target;
		this.units = units;
		this.actualValue = actualValue;
		this.instruction = instruction;
	}
	
	public String getInstruction() {
		return instruction;
	}	
	
	public T getValue(){
		return actualValue;
	}
	
	public String getUnits(){
		return units;
	}
	
	public T getTarget(){
		return target;
	}
		
	public abstract void execute();

	public boolean getIsNotCompleted() {		
		return !isCompleted;
	}
	
	public void setIsCompleted() {		
		isCompleted = true;
	}	
}
