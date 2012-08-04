package beerxml;

import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(strict=false)
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
