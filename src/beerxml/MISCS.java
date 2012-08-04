package beerxml;

import org.simpleframework.xml.ElementArray;

public class MISCS {

  @ElementArray
  private MISC[] theMiscs;

  public MISCS() {
    super();
  }

  /**
   */
  public MISC[] gettheMiscs() {
    return theMiscs;
  }
}
