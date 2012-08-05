package com.example.homebrewnavigator.bll;


public abstract class RecipeStep<T> {
	protected T target;
	protected String units;
	protected T actualValue;
	protected String instruction;	
	protected Boolean isCompleted = false;
	//setting this to true will cause the timer to start for boil
	protected Boolean isBoilStarter = false; 
	//setting this to true will cause the timer to stop for boil
	protected Boolean isBoilEnder = false; 
	
	public RecipeStep(T target, String units, T actualValue, String instruction){
		this.target = target;
		this.units = units;
		this.actualValue = actualValue;
		this.instruction = instruction;
	}
	
	public RecipeStep(T i, String units, T j, String instruction2,
			boolean isBoilStarter2, boolean isBoilEnder2) {
		this.target = i;
		this.units = units;
		this.actualValue = j;
		this.instruction = instruction2;
		this.isBoilStarter = isBoilStarter2;
		this.isBoilEnder = isBoilEnder2;
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

	public void setValue(T value) {
		actualValue = value;
	}

	public Boolean getIsBoilStarter() {		
		return isBoilStarter;
	}
	
	public Boolean getIsBoilEnder() {		
		return isBoilEnder;
	}	
}
