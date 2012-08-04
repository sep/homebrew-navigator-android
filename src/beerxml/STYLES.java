package beerxml;

import org.simpleframework.xml.ElementArray;

public class STYLES {

  @ElementArray
  private STYLE[] theStyles;

  public STYLES() {
    super();
  }

  /**
   */
  public STYLE[] gettheStyles() {
    return theStyles;
  }
}
