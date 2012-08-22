package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbAdapter extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "beer";
	
	public DbAdapter(Context context) {
		super(context, DATABASE_NAME, null, Patch.PATCHES.length);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		for (Patch p : Patch.PATCHES)
			p.apply(db);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (int i=oldVersion; i<newVersion; i++) {
			Patch.PATCHES[i].apply(db);
		}
	}
	
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (int i=oldVersion; i>newVersion; i++) {
			Patch.PATCHES[i-1].revert(db);
		}
	}
}
