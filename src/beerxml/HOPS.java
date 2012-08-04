package beerxml;

import java.util.List;
import org.simpleframework.xml.ElementList;

public class HOPS {

  @ElementList(required=true, inline=true)
  private List<HOP> theHops;

  public HOPS() {
    super();
  }

  /**
   */
  public List<HOP> gettheHops() {
    return theHops;
  }
}
