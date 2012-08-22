package db;

import android.database.sqlite.SQLiteDatabase;

class Patch {
	public static final Patch[] PATCHES = new Patch[] {
		new Patch() {
			public void apply(SQLiteDatabase db) {
				db.execSQL("create table hops (id INTEGER PRIMARY KEY, recipe_id INTEGER, name TEXT NOT NULL, version DOUBLE NOT NULL, alpha DOUBLE NOT NULL, amount DOUBLE NOT NULL, use TEXT NOT NULL, time DOUBLE NOT NULL, notes TEXT, type TEXT, form TEXT, beta DOUBLE, hsi DOUBLE, origin TEXT, substitutes TEXT, humulene DOUBLE, caryophyllene DOUBLE, cohumulone DOUBLE, myrcene DOUBLE);");
				db.execSQL("create table fermentables (id INTEGER PRIMARY KEY, recipe_id INTEGER, name TEXT NOT NULL, version DOUBLE NOT NULL, type TEXT NOT NULL, amount DOUBLE NOT NULL, yield DOUBLE NOT NULL, color DOUBLE NOT NULL, add_after_boil BOOLEAN, origin TEXT, supplier TEXT, notes TEXT, coarse_fine_diff TEXT, moisture TEXT, diastic_power TEXT, protein TEXT, max_in_batch DOUBLE, recommended_mash BOOLEAN, ibu_gal_per_lb DOUBLE);");
				db.execSQL("create table yeasts (id INTEGER PRIMARY KEY, recipe_id INTEGER, name TEXT NOT NULL, version DOUBLE NOT NULL, type TEXT NOT NULL, form TEXT NOT NULL, amount DOUBLE NOT NULL, amount_is_weight BOOLEAN, laboratory TEXT, product_id TEXT, min_temperature DOUBLE, max_temperature DOUBLE, flocculation TEXT, attenuation DOUBLE, notes TEXT, best_for TEXT, times_cultured DOUBLE, max_reuse DOUBLE, add_to_secondary BOOLEAN);");
				db.execSQL("create table miscs (id INTEGER PRIMARY KEY, recipe_id INTEGER, name TEXT NOT NULL, version DOUBLE NOT NULL, type TEXT NOT NULL, use TEXT NOT NULL, time DOUBLE NOT NULL, amount DOUBLE NOT NULL, amount_is_weight BOOLEAN, use_for TEXT, notes TEXT);");
				db.execSQL("create table styles (id INTEGER PRIMARY KEY, recipe_id INTEGER, name TEXT NOT NULL, version DOUBLE NOT NULL, category TEXT NOT NULL, category_number TEXT NOT NULL, style_letter TEXT NOT NULL, style_guide TEXT NOT NULL, type TEXT NOT NULL, og_min DOUBLE NOT NULL, og_max DOUBLE NOT NULL, fg_min DOUBLE NOT NULL, fg_max DOUBLE NOT NULL, ibu_min DOUBLE NOT NULL, ibu_max DOUBLE NOT NULL, color_min DOUBLE NOT NULL, color_max DOUBLE NOT NULL, carb_min DOUBLE, carb_max DOUBLE, abv_min DOUBLE, abv_max DOUBLE, notes TEXT, profile TEXT, ingredients TEXT, examples TEXT);");
				db.execSQL("create table recipes (id INTEGER PRIMARY KEY, name TEXT NOT NULL, version DOUBLE NOT NULL, type TEXT NOT NULL, brewer TEXT NOT NULL, asst_brewer TEXT, batch_size DOUBLE NOT NULL, boil_size DOUBLE NOT NULL, boil_time DOUBLE NOT NULL, efficiency DOUBLE, notes TEXT, taste_notes TEXT, taste_rating DOUBLE, og DOUBLE, fg DOUBLE, fermentation_stages DOUBLE, primary_age DOUBLE, primary_temp DOUBLE, secondary_age DOUBLE, secondary_temp DOUBLE, tertiary_age DOUBLE, tertiary_temp DOUBLE, age DOUBLE, age_temp DOUBLE, date TEXT, carbonation DOUBLE, forced_carbonation BOOLEAN, priming_sugar_name TEXT, carbonation_temp DOUBLE, priming_sugar_equiv DOUBLE, keg_priming_factor DOUBLE);");
			}
			public void revert(SQLiteDatabase db) {
				db.execSQL("drop table hops;");
				db.execSQL("drop table fermentables;");
				db.execSQL("drop table yeasts;");
				db.execSQL("drop table miscs;");
				db.execSQL("drop table styles;");
				db.execSQL("drop table recipes;");
			}
		},
	};

	public void apply(SQLiteDatabase db) {}
	public void revert(SQLiteDatabase db) {}
}