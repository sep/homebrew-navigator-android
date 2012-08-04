package beerxml;

import org.simpleframework.xml.Element;

public class RECIPE {

  @Element
  private String NAME;

  @Element
  private int VERSION;

  // list values: Extract, Partial Mash, All Grain
  @Element
  private String TYPE;

  @Element
  private STYLE STYLE;

  @Element
  private String BREWER;

  @Element
  private String ASST_BREWER;

  // liters
  @Element
  private double BATCH_SIZE;

  // liters
  @Element
  private double BOIL_SIZE;

  // percent
  @Element
  private double EFFICIENCY;

  @Element
  private HOPS HOPS;

  @Element
  private FERMENTABLES FERMENTABLES;

  @Element
  private MISCS MISCS;

  @Element
  private YEASTS YEASTS;

  @Element
  private String NOTES;

  @Element
  private String TASTE_NOTES;

  // 0-50.0
  @Element
  private double TASTE_RATING;

  @Element
  private double OG;

  @Element
  private double FG;

  @Element
  private int FERMENTATION_STAGES;

  // days
  @Element
  private int PRIMARY_AGE;

  // C
  @Element
  private double PRIMARY_TEMP;

  // days
  @Element
  private int SECONDARY_AGE;

  // C
  @Element
  private double SECONDARY_TEMP;

  // days
  @Element
  private int TERTIARY_AGE;

  // C
  @Element
  private double TERTIARY_TEMP;

  // days
  @Element
  private int AGE;

  // C
  @Element
  private int AGE_TEMP;

  @Element
  private String DATE;

  @Element
  private double CARBONATION;

  // really a bool.  default false.
  // list values: TRUE, FALSE
  @Element
  private String FORCED_CARBONATION;

  @Element
  private String PRIMING_SUGAR_NAME;

  // C
  @Element
  private double CARBONATION_TEMP;

  // used for math, if you arent using corn sugar
  @Element
  private double PRIMING_SUGAR_EQUIV;

  // used for math when kegging.
  @Element
  private double KEG_PRIMING_FACTOR;

  public RECIPE() {
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
   * list values: Extract, Partial Mash, All Grain
   */
  public String getTYPE() {
    return TYPE;
  }

  /**
   */
  public STYLE getSTYLE() {
    return STYLE;
  }

  /**
   */
  public String getBREWER() {
    return BREWER;
  }

  /**
   */
  public String getASST_BREWER() {
    return ASST_BREWER;
  }

  /**
   * liters
   */
  public double getBATCH_SIZE() {
    return BATCH_SIZE;
  }

  /**
   * liters
   */
  public double getBOIL_SIZE() {
    return BOIL_SIZE;
  }

  /**
   * percent
   */
  public double getEFFICIENCY() {
    return EFFICIENCY;
  }

  /**
   */
  public HOPS getHOPS() {
    return HOPS;
  }

  /**
   */
  public FERMENTABLES getFERMENTABLES() {
    return FERMENTABLES;
  }

  /**
   */
  public MISCS getMISCS() {
    return MISCS;
  }

  /**
   */
  public YEASTS getYEASTS() {
    return YEASTS;
  }

  /**
   */
  public String getNOTES() {
    return NOTES;
  }

  /**
   */
  public String getTASTE_NOTES() {
    return TASTE_NOTES;
  }

  /**
   * 0-50.0
   */
  public double getTASTE_RATING() {
    return TASTE_RATING;
  }

  /**
   */
  public double getOG() {
    return OG;
  }

  /**
   */
  public double getFG() {
    return FG;
  }

  /**
   */
  public int getFERMENTATION_STAGES() {
    return FERMENTATION_STAGES;
  }

  /**
   * days
   */
  public int getPRIMARY_AGE() {
    return PRIMARY_AGE;
  }

  /**
   * C
   */
  public double getPRIMARY_TEMP() {
    return PRIMARY_TEMP;
  }

  /**
   * days
   */
  public int getSECONDARY_AGE() {
    return SECONDARY_AGE;
  }

  /**
   * C
   */
  public double getSECONDARY_TEMP() {
    return SECONDARY_TEMP;
  }

  /**
   * days
   */
  public int getTERTIARY_AGE() {
    return TERTIARY_AGE;
  }

  /**
   * C
   */
  public double getTERTIARY_TEMP() {
    return TERTIARY_TEMP;
  }

  /**
   * days
   */
  public int getAGE() {
    return AGE;
  }

  /**
   * C
   */
  public int getAGE_TEMP() {
    return AGE_TEMP;
  }

  /**
   */
  public String getDATE() {
    return DATE;
  }

  /**
   */
  public double getCARBONATION() {
    return CARBONATION;
  }

  /**
   * really a bool.  default false.
   * list values: TRUE, FALSE
   */
  public String getFORCED_CARBONATION() {
    return FORCED_CARBONATION;
  }

  /**
   */
  public String getPRIMING_SUGAR_NAME() {
    return PRIMING_SUGAR_NAME;
  }

  /**
   * C
   */
  public double getCARBONATION_TEMP() {
    return CARBONATION_TEMP;
  }

  /**
   * used for math, if you arent using corn sugar
   */
  public double getPRIMING_SUGAR_EQUIV() {
    return PRIMING_SUGAR_EQUIV;
  }

  /**
   * used for math when kegging.
   */
  public double getKEG_PRIMING_FACTOR() {
    return KEG_PRIMING_FACTOR;
  }
}
