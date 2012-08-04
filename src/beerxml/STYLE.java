package beerxml;

import org.simpleframework.xml.Element;

public class STYLE {

  @Element(required=true)
  private String NAME;

  @Element(required=true)
  private int VERSION;

  @Element(required=true)
  private String CATEGORY;

  @Element(required=true)
  private String CATEGORY_NUMBER;

  @Element(required=true)
  private String STYLE_LETTER;

  @Element(required=true)
  private String STYLE_GUIDE;

  // list values: Lager, Ale, Mead, Wheat, Mixed, Cider
  @Element(required=true)
  private String TYPE;

  @Element(required=true)
  private double OG_MIN;

  @Element(required=true)
  private double OG_MAX;

  @Element(required=true)
  private double FG_MIN;

  @Element(required=true)
  private double FG_MAX;

  @Element(required=true)
  private int IBU_MIN;

  @Element(required=true)
  private int IBU_MAX;

  @Element(required=true)
  private int COLOR_MIN;

  @Element(required=true)
  private int COLOR_MAX;

  @Element(required=false)
  private double CARB_MIN;

  @Element(required=false)
  private double CARB_MAX;

  @Element(required=false)
  private double ABV_MIN;

  @Element(required=false)
  private double ABV_MAX;

  @Element(required=false)
  private String NOTES;

  @Element(required=false)
  private String PROFILE;

  @Element(required=false)
  private String INGREDIENTS;

  @Element(required=false)
  private String EXAMPLES;

  public STYLE() {
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
  public String getCATEGORY() {
    return CATEGORY;
  }

  /**
   */
  public String getCATEGORY_NUMBER() {
    return CATEGORY_NUMBER;
  }

  /**
   */
  public String getSTYLE_LETTER() {
    return STYLE_LETTER;
  }

  /**
   */
  public String getSTYLE_GUIDE() {
    return STYLE_GUIDE;
  }

  /**
   * list values: Lager, Ale, Mead, Wheat, Mixed, Cider
   */
  public String getTYPE() {
    return TYPE;
  }

  /**
   */
  public double getOG_MIN() {
    return OG_MIN;
  }

  /**
   */
  public double getOG_MAX() {
    return OG_MAX;
  }

  /**
   */
  public double getFG_MIN() {
    return FG_MIN;
  }

  /**
   */
  public double getFG_MAX() {
    return FG_MAX;
  }

  /**
   */
  public int getIBU_MIN() {
    return IBU_MIN;
  }

  /**
   */
  public int getIBU_MAX() {
    return IBU_MAX;
  }

  /**
   */
  public int getCOLOR_MIN() {
    return COLOR_MIN;
  }

  /**
   */
  public int getCOLOR_MAX() {
    return COLOR_MAX;
  }

  /**
   */
  public double getCARB_MIN() {
    return CARB_MIN;
  }

  /**
   */
  public double getCARB_MAX() {
    return CARB_MAX;
  }

  /**
   */
  public double getABV_MIN() {
    return ABV_MIN;
  }

  /**
   */
  public double getABV_MAX() {
    return ABV_MAX;
  }

  /**
   */
  public String getNOTES() {
    return NOTES;
  }

  /**
   */
  public String getPROFILE() {
    return PROFILE;
  }

  /**
   */
  public String getINGREDIENTS() {
    return INGREDIENTS;
  }

  /**
   */
  public String getEXAMPLES() {
    return EXAMPLES;
  }
}
