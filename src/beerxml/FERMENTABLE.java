package beerxml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class FERMENTABLE {

  @Element(required=true)
  private String NAME;

  @Element(required=true)
  private int VERSION;

  // list values: Grain, Sugar, Extract, Dry Extract, Adjunct
  @Element(required=true)
  private String TYPE;

  // kg
  @Element(required=true)
  private double AMOUNT;

  // percent
  @Element(required=true)
  private double YIELD;

  // Lovibond
  @Element(required=true)
  private double COLOR;

  // actually bool
  // list values: TRUE, FALSE
  @Element(required=false)
  private String ADD_AFTER_BOIL;

  @Element(required=false)
  private String ORIGIN;

  @Element(required=false)
  private String SUPPLIER;

  @Element(required=false)
  private String NOTES;

  // (double) percent.  only really makes sense for grain or adjunct type.
  @Element(required=false)
  private string COARSE_FINE_DIFF;

  // (double) percent.  only appropriate for grain or adjunct
  @Element(required=false)
  private string MOISTURE;

  // (double) only appropriate for grain or adjunct.
  @Element(required=false)
  private string DIASTIC_POWER;

  // (double) percent.  only appropriate for grain or adjunct.
  @Element(required=false)
  private string PROTEIN;

  // percent.
  @Element(required=false)
  private double MAX_IN_BATCH;

  // really a bool.
  // list values: TRUE, FALSE
  @Element(required=false)
  private String RECOMMENDED_MASH;

  // useful for calculating IBU
  @Element(required=false)
  private double IBU_GAL_PER_LB;

  public FERMENTABLE() {
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
   * list values: Grain, Sugar, Extract, Dry Extract, Adjunct
   */
  public String getTYPE() {
    return TYPE;
  }

  /**
   * kg
   */
  public double getAMOUNT() {
    return AMOUNT;
  }

  /**
   * percent
   */
  public double getYIELD() {
    return YIELD;
  }

  /**
   * Lovibond
   */
  public double getCOLOR() {
    return COLOR;
  }

  /**
   * actually bool
   * list values: TRUE, FALSE
   */
  public String getADD_AFTER_BOIL() {
    return ADD_AFTER_BOIL;
  }

  /**
   */
  public String getORIGIN() {
    return ORIGIN;
  }

  /**
   */
  public String getSUPPLIER() {
    return SUPPLIER;
  }

  /**
   */
  public String getNOTES() {
    return NOTES;
  }

  /**
   * (double) percent.  only really makes sense for grain or adjunct type.
   */
  public string getCOARSE_FINE_DIFF() {
    return COARSE_FINE_DIFF;
  }

  /**
   * (double) percent.  only appropriate for grain or adjunct
   */
  public string getMOISTURE() {
    return MOISTURE;
  }

  /**
   * (double) only appropriate for grain or adjunct.
   */
  public string getDIASTIC_POWER() {
    return DIASTIC_POWER;
  }

  /**
   * (double) percent.  only appropriate for grain or adjunct.
   */
  public string getPROTEIN() {
    return PROTEIN;
  }

  /**
   * percent.
   */
  public double getMAX_IN_BATCH() {
    return MAX_IN_BATCH;
  }

  /**
   * really a bool.
   * list values: TRUE, FALSE
   */
  public String getRECOMMENDED_MASH() {
    return RECOMMENDED_MASH;
  }

  /**
   * useful for calculating IBU
   */
  public double getIBU_GAL_PER_LB() {
    return IBU_GAL_PER_LB;
  }
}
