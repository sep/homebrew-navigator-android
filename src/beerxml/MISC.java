package beerxml;

import org.simpleframework.xml.Element;

public class MISC {

  @Element
  private String NAME;

  @Element
  private int VERSION;

  // list values: Spice, Fining, Water Agent, Herb, Flavor, Other
  @Element
  private String TYPE;

  // list values: Boil, Mash, Primary, Secondary, Bottling
  @Element
  private String USE;

  // minutes
  @Element
  private double TIME;

  // volume or weight.  kg or L
  @Element
  private double AMOUNT;

  // really a bool.  defaults to false
  // list values: TRUE, FALSE
  @Element
  private String AMOUNT_IS_WEIGHT;

  @Element
  private String USE_FOR;

  @Element
  private String NOTES;

  public MISC() {
    super();
  }

  /**
   */
  public String getNAME() {
    return NAME;
  }

  /**
   */
  public int getVERSION() {
    return VERSION;
  }

  /**
   * list values: Spice, Fining, Water Agent, Herb, Flavor, Other
   */
  public String getTYPE() {
    return TYPE;
  }

  /**
   * list values: Boil, Mash, Primary, Secondary, Bottling
   */
  public String getUSE() {
    return USE;
  }

  /**
   * minutes
   */
  public double getTIME() {
    return TIME;
  }

  /**
   * volume or weight.  kg or L
   */
  public double getAMOUNT() {
    return AMOUNT;
  }

  /**
   * really a bool.  defaults to false
   * list values: TRUE, FALSE
   */
  public String getAMOUNT_IS_WEIGHT() {
    return AMOUNT_IS_WEIGHT;
  }

  /**
   */
  public String getUSE_FOR() {
    return USE_FOR;
  }

  /**
   */
  public String getNOTES() {
    return NOTES;
  }
}
