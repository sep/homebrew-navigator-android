package beerxml;

import org.simpleframework.xml.ElementArray;

public class HOPS {

  @ElementArray
  private HOP[] theHops;

  public HOPS() {
    super();
  }

  /**
   */
  public HOP[] gettheHops() {
    return theHops;
  }
}
