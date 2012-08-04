package beerxml;

import java.util.List;
import org.simpleframework.xml.ElementList;

public class FERMENTABLES {

  @ElementList(required=true)
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
