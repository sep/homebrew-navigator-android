package beerxml;

import org.simpleframework.xml.ElementArray;

public class YEASTS {

  @ElementArray
	private YEAST[] theYeasts;

  public YEASTS() {
    super();
  }

  /**
   */
  public YEAST[] gettheYeasts() {
    return theYeasts;
  }
}
