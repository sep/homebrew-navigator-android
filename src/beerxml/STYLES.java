package beerxml;

import java.util.List;
import org.simpleframework.xml.ElementList;

public class STYLES {

  @ElementList(required=true)
  private List<STYLE> theStyles;

  public STYLES() {
    super();
  }

  /**
   */
  public List<STYLE> gettheStyles() {
    return theStyles;
  }
}
