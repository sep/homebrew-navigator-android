package beerxml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class RECIPE {

  @Element(required=true)
  private String NAME;

  @Element(required=true)
  private int VERSION;

  // list values: Extract, Partial Mash, All Grain
  @Element(required=true)
  private String TYPE;

  @Element(required=true)
  private STYLE STYLE;

  @Element(required=true)
  private String BREWER;

  @Element(required=false)
  private String ASST_BREWER;

  // liters
  @Element(required=true)
  private double BATCH_SIZE;

  // liters
  @Element(required=true)
  private double BOIL_SIZE;

  // minutes
  @Element(required=true)
  private double BOIL_TIME;

  // percent
  @Element(required=false)
  private double EFFICIENCY;

  @Element(required=true)
  private HOPS HOPS;

  @Element(required=true)
  private FERMENTABLES FERMENTABLES;

  @Element(required=true)
  private MISCS MISCS;

  @Element(required=true)
  private YEASTS YEASTS;

  @Element(required=false)
  private String NOTES;

  @Element(required=false)
  private String TASTE_NOTES;

  // 0-50.0
  @Element(required=false)
  private double TASTE_RATING;

  @Element(required=false)
  private double OG;

  @Element(required=false)
  private double FG;

  @Element(required=false)
  private int FERMENTATION_STAGES;

  // days
  @Element(required=false)
  private int PRIMARY_AGE;

  // C
  @Element(required=false)
  private double PRIMARY_TEMP;

  // days
  @Element(required=false)
  private int SECONDARY_AGE;

  // C
  @Element(required=false)
  private double SECONDARY_TEMP;

  // days
  @Element(required=false)
  private int TERTIARY_AGE;

  // C
  @Element(required=false)
  private double TERTIARY_TEMP;

  // days
  @Element(required=false)
  private int AGE;

  // C
  @Element(required=false)
  private int AGE_TEMP;

  @Element(required=false)
  private String DATE;

  @Element(required=false)
  private double CARBONATION;

  // really a bool.  default false.
  // list values: TRUE, FALSE
  @Element(required=false)
  private String FORCED_CARBONATION;

  @Element(required=true)
  private String PRIMING_SUGAR_NAME;

  // C
  @Element(required=true)
  private double CARBONATION_TEMP;

  // used for math, if you arent using corn sugar
  @Element(required=true)
  private double PRIMING_SUGAR_EQUIV;

  // used for math when kegging.
  @Element(required=true)
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
   * minutes
   */
  public double getBOIL_TIME() {
    return BOIL_TIME;
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
