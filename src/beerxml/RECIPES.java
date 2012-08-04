package beerxml;

import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class RECIPES {

  @ElementList(required=true, inline=true)
  private List<RECIPE> theRecipes;

  public RECIPES() {
    super();
  }

  /**
   */
  public List<RECIPE> gettheRecipes() {
    return theRecipes;
  }
}
