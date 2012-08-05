package com.example.homebrewnavigator;

import android.app.Application;
import android.content.Context;

public class MyContext extends Application {

	private static MyContext instance;

	public MyContext() {
		instance = this;
	}

	public static Context getContext() {
		return instance;
	}

}