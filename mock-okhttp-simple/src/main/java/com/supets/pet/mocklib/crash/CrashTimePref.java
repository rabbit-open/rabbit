package com.supets.pet.mocklib.crash;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class CrashTimePref extends BasePref {

	private static final String CRASHTIME = "expire";

	private static final String Name="crash_config";

	public static boolean isCrash() {
		SharedPreferences preferences = getPref(Name);
		long expire = preferences.getLong(CRASHTIME, 0);
		long now = System.currentTimeMillis();
		if ((now-expire )/1000>= 20) {
			saveCurrenTime();
			return true;
		}
		return false;
	}
	
	public static void saveCurrenTime() {
		Editor editor = edit(Name);
		editor = editor.putLong(CRASHTIME, System.currentTimeMillis());
		editor.commit();
	}

	public static void clear() {
		BasePref.clear(Name);
	}

}
