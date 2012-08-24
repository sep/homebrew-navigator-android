package utility;

import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbHelper {
	public static <T> List<T> query(SQLiteDatabase db, String sql, String[] params, Selector<T, Cursor> mapper) {
		Cursor c = db.rawQuery(sql, params);
		
		return CursorHelper.loop(c, mapper);
	}
	public static <T> T one(SQLiteDatabase db, String sql, String[] params, Selector<T, Cursor> mapper) {
		Cursor c = db.rawQuery(sql, params);
		
		return CursorHelper.first(c, mapper);
	}
}