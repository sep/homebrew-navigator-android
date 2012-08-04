package beerxml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class YEAST {

  @Element(required=true)
  private String NAME;

  @Element(required=true)
  private int VERSION;

  // list values: Ale, Lager, Wheat, Wine, Champagne
  @Element(required=true)
  private String TYPE;

  // list values: Liquid, Dry, Slant, Culture
  @Element(required=true)
  private String FORM;

  // volume or weight.  kg or l.
  @Element(required=true)
  private double AMOUNT;

  // really a bool.
  // list values: TRUE, FALSE
  @Element(required=false)
  private String AMOUNT_IS_WEIGHT;

  @Element(required=false)
  private String LABORATORY;

  @Element(required=false)
  private String PRODUCT_ID;

  @Element(required=false)
  private double MIN_TEMPERATURE;

  @Element(required=false)
  private double MAX_TEMPERATURE;

  // list values: Low, Medium, High, Very High
  @Element(required=false)
  private String FLOCCULATION;

  // percent
  @Element(required=false)
  private double ATTENUATION;

  @Element(required=false)
  private String NOTES;

  @Element(required=false)
  private String BEST_FOR;

  // number of times reused
  @Element(required=false)
  private int TIMES_CULTURED;

  @Element(required=false)
  private int MAX_REUSE;

  // for secondary fermentation. default FALSE.
  // list values: TRUE, FALSE
  @Element(required=false)
  private String ADD_TO_SECONDARY;

  public YEAST() {
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
   * list values: Ale, Lager, Wheat, Wine, Champagne
   */
  public String getTYPE() {
    return TYPE;
  }

  /**
   * list values: Liquid, Dry, Slant, Culture
   */
  public String getFORM() {
    return FORM;
  }

  /**
   * volume or weight.  kg or l.
   */
  public double getAMOUNT() {
    return AMOUNT;
  }

  /**
   * really a bool.
   * list values: TRUE, FALSE
   */
  public String getAMOUNT_IS_WEIGHT() {
    return AMOUNT_IS_WEIGHT;
  }

  /**
   */
  public String getLABORATORY() {
    return LABORATORY;
  }

  /**
   */
  public String getPRODUCT_ID() {
    return PRODUCT_ID;
  }

  /**
   */
  public double getMIN_TEMPERATURE() {
    return MIN_TEMPERATURE;
  }

  /**
   */
  public double getMAX_TEMPERATURE() {
    return MAX_TEMPERATURE;
  }

  /**
   * list values: Low, Medium, High, Very High
   */
  public String getFLOCCULATION() {
    return FLOCCULATION;
  }

  /**
   * percent
   */
  public double getATTENUATION() {
    return ATTENUATION;
  }

  /**
   */
  public String getNOTES() {
    return NOTES;
  }

  /**
   */
  public String getBEST_FOR() {
    return BEST_FOR;
  }

  /**
   * number of times reused
   */
  public int getTIMES_CULTURED() {
    return TIMES_CULTURED;
  }

  /**
   */
  public int getMAX_REUSE() {
    return MAX_REUSE;
  }

  /**
   * for secondary fermentation. default FALSE.
   * list values: TRUE, FALSE
   */
  public String getADD_TO_SECONDARY() {
    return ADD_TO_SECONDARY;
  }
}
