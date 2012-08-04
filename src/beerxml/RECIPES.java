package beerxml;

import java.util.List;
import org.simpleframework.xml.ElementList;

public class RECIPES {

  @ElementList(required=true)
  private List<Recipe> theRecipes;

  public RECIPES() {
    super();
  }

  /**
   */
  public List<Recipe> gettheRecipes() {
    return theRecipes;
  }
}
