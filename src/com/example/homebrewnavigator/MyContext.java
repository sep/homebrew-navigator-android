package com.example.homebrewnavigator;

import db.DbAdapter;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

public class MyContext extends Application {

	private static MyContext _instance;
	private static DbAdapter _database;


	public MyContext() {
		_instance = this;
		_database = new DbAdapter(_instance);
	}

	public static Context getContext() {
		return _instance;
	}
	
	public static SQLiteOpenHelper getDb() {
		return _database;
	}

}
