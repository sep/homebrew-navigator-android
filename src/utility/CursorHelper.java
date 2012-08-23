package utility;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

public class CursorHelper {
	public static <T> List<T> loop(Cursor c, Selector<T, Cursor> selector) {
		ArrayList<T> items = new ArrayList<T>();

		if (c.getCount() == 0)
			return items;

		c.moveToFirst();
		do {
			items.add(selector.map(c));
		} while(c.moveToNext());
		
		return items;
	}
	
	public static <T> T first(Cursor c, Selector<T, Cursor> selector) {
		if (c.getCount() == 0)
			return null;

		c.moveToFirst();
		return selector.map(c);
	}
}
