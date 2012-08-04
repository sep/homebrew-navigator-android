package beerxml;

import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class STYLES {

  @ElementList(required=true, inline=true)
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
