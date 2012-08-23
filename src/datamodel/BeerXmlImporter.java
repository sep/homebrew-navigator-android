package datamodel;

import java.io.InputStream;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import beerxml.FERMENTABLE;
import beerxml.HOP;
import beerxml.MISC;
import beerxml.RECIPE;
import beerxml.RECIPES;
import beerxml.YEAST;
import db.ContentValueBuilder;

public class BeerXmlImporter {
	
	public Boolean importRecipesFromXml(InputStream xmlStream, SQLiteOpenHelper dbHelper) {
		try {
			Serializer serializer = new Persister();
			List<RECIPE> recipes = serializer.read(RECIPES.class,  xmlStream).gettheRecipes();
			for (RECIPE recipe : recipes) {
				InsertRecipe(recipe, dbHelper);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean getBool(String boolStr) {
		return boolStr == null ? false : boolStr.equalsIgnoreCase("TRUE");
	}
	
	private double getDouble(String doubleStr) {
		try {
			return Double.parseDouble(doubleStr);
		} catch (Exception e) {
			return 0;
		}
	}
	
	private void InsertRecipe(RECIPE recipe, SQLiteOpenHelper dbHelper) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			db.beginTransaction();
			long recipeId = dbHelper.getWritableDatabase().insert("recipes", null, ContentValueBuilder.Create()
					.String("asst_brewer", recipe.getASST_BREWER())
					.String("brewer", recipe.getBREWER())
					.String("date", recipe.getDATE())
					.Boolean("forced_carbonation",  getBool(recipe.getFORCED_CARBONATION()))
					.Integer("age", (int)recipe.getAGE())
					.Integer("age_temp", (int)recipe.getAGE_TEMP())
					.Double("batch_size", recipe.getBATCH_SIZE())
					.Double("boil_size", recipe.getBOIL_SIZE())
					.Integer("carbonation", (int)recipe.getCARBONATION())
					.Integer("boil_time", (int)recipe.getBOIL_TIME())
					.String("date", recipe.getDATE())
					.Double("efficiency", recipe.getEFFICIENCY())
					.Integer("fermentation_stages", (int)recipe.getFERMENTATION_STAGES())
					.Double("fg", recipe.getFG())
					.Double("og", recipe.getOG())
					.Double("keg_priming_factor", recipe.getKEG_PRIMING_FACTOR())
					.String("name", recipe.getNAME())
					.String("notes", recipe.getNOTES())
					.Integer("primary_age", (int)recipe.getPRIMARY_AGE())
					.Integer("primary_temp", (int)recipe.getPRIMARY_TEMP())
					.Integer("secondary_age", (int)recipe.getSECONDARY_AGE())
					.Integer("secondary_temp", (int)recipe.getSECONDARY_TEMP())
					.Integer("tertiary_age", (int)recipe.getTERTIARY_AGE())
					.Integer("tertiary_temp", (int)recipe.getTERTIARY_TEMP())
					.Double("priming_sugar_equiv", recipe.getPRIMING_SUGAR_EQUIV())
					.String("priming_sugar_name", recipe.getPRIMING_SUGAR_NAME())
					.String("taste_notes", recipe.getTASTE_NOTES())
					.Double("taste_rating", recipe.getTASTE_RATING())
					.String("type", recipe.getTYPE())
					.Double("version", recipe.getVERSION())
					.getValues());
			
			for (HOP hop : recipe.getHOPS().gettheHops()) {
				dbHelper.getWritableDatabase().insert("hops", null, ContentValueBuilder.Create()
						.String("form", hop.getFORM())
						.String("name", hop.getNAME())
						.String("notes", hop.getNOTES())
						.String("origin", hop.getORIGIN())
						.String("substitutes", hop.getSUBSTITUTES())
						.String("type", hop.getTYPE())
						.String("use", hop.getUSE())
						.Double("alpha", hop.getALPHA())
						.Double("amount", hop.getAMOUNT())
						.Double("beta", hop.getBETA())
						.Double("caryophyllene", hop.getCARYOPHYLLENE())
						.Double("cohumulone", hop.getCOHUMULONE())
						.Double("hsi", hop.getHSI())
						.Double("humulene", hop.getHUMULENE())
						.Double("myrcene", hop.getMYRCENE())
						.Double("time", hop.getTIME())
						.Double("version", hop.getVERSION())
						.Long("recipe_id", recipeId)
						.getValues());
			}

			for (FERMENTABLE f : recipe.getFERMENTABLES().gettheFermentables()) {
				dbHelper.getWritableDatabase().insert("fermentables", null, ContentValueBuilder.Create()
						.Long("recipe_id", recipeId)
						.Boolean("add_after_boil", getBool(f.getADD_AFTER_BOIL()))
						.Double("coarse_fine_diff", getDouble(f.getCOARSE_FINE_DIFF()))
						.Double("amount", f.getAMOUNT())
						.Double("color", f.getCOLOR())
						.Double("diastic_power", getDouble(f.getDIASTIC_POWER()))
						.Double("ibu_gal_per_lb", f.getIBU_GAL_PER_LB())
						.Double("max_in_batch", f.getMAX_IN_BATCH())
						.Double("moisture", getDouble(f.getMOISTURE()))
						.String("name", f.getNAME())
						.String("notes", f.getNOTES())
						.String("origin", f.getORIGIN())
						.Double("protein", getDouble(f.getPROTEIN()))
						.Boolean("recommended_mash", getBool(f.getRECOMMENDED_MASH()))
						.String("supplier", f.getSUPPLIER())
						.String("type", f.getTYPE())
						.Double("yield", f.getYIELD())
						.Double("version", f.getVERSION())
						.Double("moisture", getDouble(f.getMOISTURE()))
						.getValues());
			}
			
			if (recipe.getMISCS() != null && recipe.getMISCS().gettheMiscs() != null) {
				for (MISC misc : recipe.getMISCS().gettheMiscs()) {
					dbHelper.getWritableDatabase().insert("miscs", null, ContentValueBuilder.Create()
							.Long("recipe_id", recipeId)
							.Boolean("amount_is_weight", getBool(misc.getAMOUNT_IS_WEIGHT()))
							.String("name", misc.getNAME())
							.String("notes", misc.getNOTES())
							.String("type", misc.getTYPE())
							.String("use", misc.getUSE())
							.String("use_for", misc.getUSE_FOR())
							.Double("amount", misc.getAMOUNT())
							.Integer("time", (int)misc.getTIME())
							.Double("version", misc.getVERSION())
							.getValues());
				}
			}
			
			for (YEAST yeast : recipe.getYEASTS().gettheYeasts()) {
				dbHelper.getWritableDatabase().insert("yeasts", null, ContentValueBuilder.Create()
						.Long("recipe_id", recipeId)
						.Boolean("add_to_secondary", getBool(yeast.getADD_TO_SECONDARY()))
						.Boolean("amount_is_weight", getBool(yeast.getAMOUNT_IS_WEIGHT()))
						.String("best_for", yeast.getBEST_FOR())
						.String("flocculation", yeast.getFLOCCULATION())
						.String("form", yeast.getFORM())
						.String("laboratory", yeast.getLABORATORY())
						.String("name", yeast.getNAME())
						.String("notes", yeast.getNOTES())
						.String("product_id", yeast.getPRODUCT_ID())
						.String("type", yeast.getTYPE())
						.Double("amount", yeast.getAMOUNT())
						.Double("attenuation", yeast.getATTENUATION())
						.Double("max_reuse", yeast.getMAX_REUSE())
						.Integer("max_temperature", (int)yeast.getMAX_TEMPERATURE())
						.Integer("times_cultured", (int)yeast.getTIMES_CULTURED())
						.Double("version", yeast.getVERSION())
						.getValues());
			}
			
			dbHelper.getWritableDatabase().insert("styles", null, ContentValueBuilder.Create()
					.Long("recipe_id", recipeId)
					.String("category", recipe.getSTYLE().getCATEGORY())
					.String("category_number", recipe.getSTYLE().getCATEGORY_NUMBER())
					.String("examples", recipe.getSTYLE().getEXAMPLES())
					.String("ingredients", recipe.getSTYLE().getINGREDIENTS())
					.String("name", recipe.getSTYLE().getNAME())
					.String("notes", recipe.getSTYLE().getNOTES())
					.String("profile", recipe.getSTYLE().getPROFILE())
					.String("style_guide", recipe.getSTYLE().getSTYLE_GUIDE())
					.String("style_letter", recipe.getSTYLE().getSTYLE_LETTER())
					.String("type", recipe.getSTYLE().getTYPE())
					.Double("abv_max", recipe.getSTYLE().getABV_MAX())
					.Double("abv_min", recipe.getSTYLE().getABV_MIN())
					.Double("ibu_max", recipe.getSTYLE().getIBU_MAX())
					.Double("ibu_min", recipe.getSTYLE().getIBU_MIN())
					.Double("carb_max", recipe.getSTYLE().getCARB_MAX())
					.Double("carb_min", recipe.getSTYLE().getCARB_MIN())
					.Double("og_max", recipe.getSTYLE().getOG_MAX())
					.Double("og_min", recipe.getSTYLE().getOG_MIN())
					.Double("fg_max", recipe.getSTYLE().getFG_MAX())
					.Double("fg_min", recipe.getSTYLE().getFG_MIN())
					.Double("color_max", recipe.getSTYLE().getCOLOR_MAX())
					.Double("color_min", recipe.getSTYLE().getCOLOR_MIN())
					.Double("version", recipe.getSTYLE().getVERSION())
					.getValues());
			db.setTransactionSuccessful();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}

}
