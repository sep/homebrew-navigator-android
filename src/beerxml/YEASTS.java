package beerxml;

import java.util.List;
import org.simpleframework.xml.ElementList;

public class YEASTS {

  @ElementList(required=true, inline=true)
  private List<YEAST> theYeasts;

  public YEASTS() {
    super();
  }

  /**
   */
  public List<YEAST> gettheYeasts() {
    return theYeasts;
  }
}
