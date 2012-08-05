import java.io.File;

import junit.framework.TestCase;

import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.example.homebrewnavigator.bll.Recipe;

public class BeerXMLSpike extends TestCase {
	@Test
	public void WhatEver() throws Exception {
		Serializer serializer = new Persister();
		File source = new File("C:\\Users\\atniptw\\Temp\\recipes.xml");
		Recipe recipe = serializer.read(Recipe.class, source);
		System.out.print(recipe.getName());
	}
}
