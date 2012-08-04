package beerxml;

import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class FERMENTABLES {

  @ElementList(required=true, inline=true)
  private List<FERMENTABLE> theFermentables;

  public FERMENTABLES() {
    super();
  }

  /**
   */
  public List<FERMENTABLE> gettheFermentables() {
    return theFermentables;
  }
}
