package com.example.homebrewnavigator.test;

import java.util.*;

import com.example.homebrewnavigator.bll.*;

import junit.framework.Assert;
import junit.framework.TestCase;

public class RecipeBookTest extends TestCase {

	private RecipeBook daBook;
	
	@Override
	protected void setUp() throws Exception {		
		super.setUp();		
		daBook = new RecipeBook(new FakeRecipeRepository());		
	}

	public void testGetRecipeWillReturnMyRecipeByName(){
		
		Recipe r = daBook.GetRecipe("My Special Brew");
		
		Assert.assertEquals("My Special Brew", r.getName());		
	}
	
	public void testFireStepCriteriaMetIntentWhenWhenTimesUp() throws InterruptedException{
		Recipe r = daBook.GetRecipe("My Special Brew");
		MockAlarmManager d = new MockAlarmManager();		
		TimedStep step2 = new TimedStep(1, d); 
		r.addStep(step2);
		RecipeStep rs = r.getCurrentStep();
				
		Assert.assertTrue(d.fired);				
	}
	
	public void testFireIntentStepCriteriaMetTargetForTemperature(){
		Recipe r = daBook.GetRecipe("My Special Brew");
		MockAlarmManager d = new MockAlarmManager();		
		TimedStep step2 = new TimedStep(1, d); 
		r.addStep(step2);
		RecipeStep rs = r.getCurrentStep();
				
		Assert.assertTrue(d.fired);		
	}
	
	//TODO: Need a way to tell the step to start executing
	//Manual Step will fire as soon as it is start executing
	//An intent is fired so the activity can set an notification	
	
	public void testGetCurrentStepReturnCorrectStep(){
		Recipe r = new Recipe();
		ManualRecipeStep step1 = new ManualRecipeStep("Blah");
		step1.setIsCompleted();
		ManualRecipeStep step2 = new ManualRecipeStep("Blah");
		ManualRecipeStep step3 = new ManualRecipeStep("Blah");	
		ManualRecipeStep step4 = new ManualRecipeStep("Blah");
		
		r.addStep(step1);
		r.addStep(step2);
		r.addStep(step3);
		r.addStep(step4);
		
		RecipeStep currentStep = r.getCurrentStep();
		List<RecipeStep> nexSteps = r.getNextSteps();
		
		Assert.assertEquals(step2, currentStep);
		Assert.assertEquals(2, nexSteps.size());
		
	}
	
	
}
