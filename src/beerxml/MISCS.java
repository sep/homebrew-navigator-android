package beerxml;

import java.util.List;
import org.simpleframework.xml.ElementList;

public class MISCS {

  @ElementList(required=true, inline=true)
  private List<MISC> theMiscs;

  public MISCS() {
    super();
  }

  /**
   */
  public List<MISC> gettheMiscs() {
    return theMiscs;
  }
}
