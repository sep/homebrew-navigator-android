package db;

import android.content.ContentValues;

public class ContentValueBuilder {
	public static ContentValueBuilder Create() {
		return new ContentValueBuilder();
	}
	
	private ContentValues _contentValues;
	
	private ContentValueBuilder() {
		_contentValues = new ContentValues();
	}
	
	public ContentValueBuilder String(String name, String value) {
		_contentValues.put(name, value);
		return this;
	}
	
	public ContentValueBuilder Double(String name, Double value) {
		_contentValues.put(name, value);
		return this;
	}
	
	public ContentValueBuilder Integer(String name, int value) {
		_contentValues.put(name, value);
		return this;
	}
	
	public ContentValueBuilder Long(String name, long value) {
		_contentValues.put(name, value);
		return this;
	}
	
	public ContentValueBuilder Boolean(String name, boolean value) {
		_contentValues.put(name, value);
		return this;
	}
	
	public ContentValues getValues() {
		return _contentValues;
	}
}
