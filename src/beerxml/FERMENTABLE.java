package beerxml;

import org.simpleframework.xml.Element;

public class FERMENTABLE {

  @Element
  private String NAME;

  @Element
  private int VERSION;

  // list values: Grain, Sugar, Extract, Dry Extract, Adjunct
  @Element
  private String TYPE;

  // kg
  @Element
  private double AMOUNT;

  // percent
  @Element
  private double YIELD;

  // Lovibond
  @Element
  private double COLOR;

  // actually bool
  // list values: TRUE, FALSE
  @Element
  private String ADD_AFTER_BOIL;

  @Element
  private String ORIGIN;

  @Element
  private String SUPPLIER;

  @Element
  private String NOTES;

  // percent.  only really makes sense for grain or adjunct type.
  @Element
  private double COARSE_FINE_DIFF;

  // percent.  only appropriate for grain or adjunct
  @Element
  private double MOISTURE;

  // only appropriate for grain or adjunct.
  @Element
  private double DIASTIC_POWER;

  // percent.  only appropriate for grain or adjunct.
  @Element
  private double PROTEIN;

  // percent.
  @Element
  private double MAX_IN_BATCH;

  // really a bool.
  // list values: TRUE, FALSE
  @Element
  private String RECOMMENDED_MASH;

  // useful for calculating IBU
  @Element
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
   * percent.  only really makes sense for grain or adjunct type.
   */
  public double getCOARSE_FINE_DIFF() {
    return COARSE_FINE_DIFF;
  }

  /**
   * percent.  only appropriate for grain or adjunct
   */
  public double getMOISTURE() {
    return MOISTURE;
  }

  /**
   * only appropriate for grain or adjunct.
   */
  public double getDIASTIC_POWER() {
    return DIASTIC_POWER;
  }

  /**
   * percent.  only appropriate for grain or adjunct.
   */
  public double getPROTEIN() {
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
