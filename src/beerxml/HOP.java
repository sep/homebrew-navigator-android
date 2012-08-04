package beerxml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class HOP {

  @Element(required=true)
  private String NAME;

  @Element(required=true)
  private int VERSION;

  @Element(required=true)
  private double ALPHA;

  @Element(required=true)
  private double AMOUNT;

  // list values: Boil, Dry Hop, Mash, First Wort, Aroma
  @Element(required=true)
  private String USE;

  // minutes
  @Element(required=true)
  private double TIME;

  @Element(required=false)
  private String NOTES;

  // list values: Bittering, Aroma, Both
  @Element(required=false)
  private String TYPE;

  // list values: Pellet, Plug, Leaf
  @Element(required=false)
  private String FORM;

  // percentage
  @Element(required=false)
  private double BETA;

  // percentage
  @Element(required=false)
  private double HSI;

  @Element(required=false)
  private String ORIGIN;

  @Element(required=false)
  private String SUBSTITUTES;

  // percentage
  @Element(required=false)
  private double HUMULENE;

  // percentage
  @Element(required=false)
  private double CARYOPHYLLENE;

  // percentage
  @Element(required=false)
  private double COHUMULONE;

  // percentage
  @Element(required=false)
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
