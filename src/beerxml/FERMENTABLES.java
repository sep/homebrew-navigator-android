package beerxml;

import org.simpleframework.xml.ElementArray;

public class FERMENTABLES {

  @ElementArray
  private FERMENTABLE[] theFermentables;

  public FERMENTABLES() {
    super();
  }

  /**
   */
  public FERMENTABLE[] gettheFermentables() {
    return theFermentables;
  }
}
