package beerxml;

import org.simpleframework.xml.ElementArray;

public class RECIPES {

  @ElementArray
  private RECIPE[] theRecipes;

  public RECIPES() {
    super();
  }

  /**
   */
  public RECIPE[] gettheRecipes() {
    return theRecipes;
  }
}
