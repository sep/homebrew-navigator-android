package beerxml;

import org.simpleframework.xml.Element;

public class HOP {

  @Element
  private String NAME;

  @Element
  private int VERSION;

  @Element
  private double ALPHA;

  @Element
  private double AMOUNT;

  // list values: Boil, Dry Hop, Mash, First Wort, Aroma
  @Element
  private String USE;

  // minutes
  @Element
  private double TIME;

  @Element
  private String NOTES;

  // list values: Bittering, Aroma, Both
  @Element
  private String TYPE;

  // list values: Pellet, Plug, Leaf
  @Element
  private String FORM;

  // percentage
  @Element
  private double BETA;

  // percentage
  @Element
  private double HSI;

  @Element
  private String ORIGIN;

  @Element
  private String SUBSTITUTES;

  // percentage
  @Element
  private double HUMULENE;

  // percentage
  @Element
  private double CARYOPHYLLENE;

  // percentage
  @Element
  private double COHUMULONE;

  // percentage
  @Element
  private double MYRCENE;

  public HOP() {
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
   */
  public double getALPHA() {
    return ALPHA;
  }

  /**
   */
  public double getAMOUNT() {
    return AMOUNT;
  }

  /**
   * list values: Boil, Dry Hop, Mash, First Wort, Aroma
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
   */
  public String getNOTES() {
    return NOTES;
  }

  /**
   * list values: Bittering, Aroma, Both
   */
  public String getTYPE() {
    return TYPE;
  }

  /**
   * list values: Pellet, Plug, Leaf
   */
  public String getFORM() {
    return FORM;
  }

  /**
   * percentage
   */
  public double getBETA() {
    return BETA;
  }

  /**
   * percentage
   */
  public double getHSI() {
    return HSI;
  }

  /**
   */
  public String getORIGIN() {
    return ORIGIN;
  }

  /**
   */
  public String getSUBSTITUTES() {
    return SUBSTITUTES;
  }

  /**
   * percentage
   */
  public double getHUMULENE() {
    return HUMULENE;
  }

  /**
   * percentage
   */
  public double getCARYOPHYLLENE() {
    return CARYOPHYLLENE;
  }

  /**
   * percentage
   */
  public double getCOHUMULONE() {
    return COHUMULONE;
  }

  /**
   * percentage
   */
  public double getMYRCENE() {
    return MYRCENE;
  }
}
