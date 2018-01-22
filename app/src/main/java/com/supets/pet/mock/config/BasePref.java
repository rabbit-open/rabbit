package com.supets.pet.mock.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.supets.commons.App;


 class BasePref {

	protected static SharedPreferences getPref(String name) {
		if (TextUtils.isEmpty(name)) {
			throw new NullPointerException("Shared preferences name is null or empty");
		}
		return App.INSTANCE.getSharedPreferences(name, Context.MODE_PRIVATE);
	}

	protected static SharedPreferences.Editor edit(String name) {
		return getPref(name).edit();
	}

	public static void clear(String name) {
		edit(name).clear().commit();
	}


}
