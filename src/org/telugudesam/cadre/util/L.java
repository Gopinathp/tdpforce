package org.telugudesam.cadre.util;

import org.json.JSONObject;
import org.telugudesam.cadre.BuildConfig;

import android.content.SharedPreferences;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.google.gson.Gson;

/**
 * GreenLogger class Features: <br>
 * 1. Logging is made easy and manageable <br>
 * 2. Turns off debug/verbose logs on production builds.<br>
 * 3. Easy log filtering on production devices. <br>
 * Filter by "GreenLogger" on adb logcat. <br>
 * (Usage: adb logcat | grep GreenLo)
 * 
 * @author gopinath
 * 
 */
public class L {

	public enum LOG_LEVEL {
		VERBOSE, DEBUG, INFO, WARN, ERROR
	}

	static LruCache<Integer, String> logs = new LruCache<Integer, String>(200);
	static int logIndex = 0;

	public static final LOG_LEVEL logLEVEL = (BuildConfig.DEBUG) ? LOG_LEVEL.VERBOSE
			: LOG_LEVEL.ERROR;
	private static final String TAG = "GreenLogger";

	public static void v(String message) {
		if (L.logLEVEL.ordinal() <= LOG_LEVEL.VERBOSE.ordinal()) {
			Log.v(TAG,
					Thread.currentThread().getStackTrace()[3].getFileName()
							+ "["
							+ Thread.currentThread().getStackTrace()[3]
									.getLineNumber() + "]:" + message);
		} else {
			logs.put(logIndex++, message);
			if (logIndex >= logs.maxSize()) {
				logIndex = 0;
			}
		}
	}

	public static void d(String message) {
		if (L.logLEVEL.ordinal() <= LOG_LEVEL.DEBUG.ordinal()) {
			Log.d(TAG,
					Thread.currentThread().getStackTrace()[3].getFileName()
							+ "["
							+ Thread.currentThread().getStackTrace()[3]
									.getLineNumber() + "]:" + message);
		}
	}

	public static void i(String message) {
		if (L.logLEVEL.ordinal() <= LOG_LEVEL.INFO.ordinal()) {
			Log.i(TAG, message);
		}
	}

	public static void w(String message) {
		if (L.logLEVEL.ordinal() <= LOG_LEVEL.WARN.ordinal()) {
			Log.w(TAG, message);
		}
	}

	public static void e(String message) {
		if (L.logLEVEL.ordinal() <= LOG_LEVEL.ERROR.ordinal()) {
			Log.e(TAG, message);
		}
	}

	public static void constructEvent(String category, String action,
			String label, String value) {
		Log.v(TAG + "ana" + category, action + " label: " + label
				+ " , value = " + value);

	}

	/**
	 * Prints the sharedpreferences file passed to this app.
	 * 
	 * @param mPrefs
	 */
	public static void print(SharedPreferences mPrefs) {
		v(new Gson().toJson(mPrefs.getAll()));
	}

	public static void v(String tag, String notifMsg) {
		v(tag + notifMsg);
	}

	public static void print(String string, SharedPreferences sharedPreferences) {
		L.d(string);
		L.print(sharedPreferences);
	}

	/**
	 * Prints the given exception so that it can be captured using GreenLogger
	 * tag.
	 * 
	 * @param e
	 */
	public static void print(Exception e) {
		if (e != null) {
			L.e("Exception: " + Log.getStackTraceString(e));
		} else
			L.d("exception which is null");

	}

	public static void v(JSONObject jsonObject) {
		L.v(jsonObject.toString());
	}

	public static void d(String tag, String msg) {
		d(tag + msg);
	}

	public static String getProdLogs() {
		return new JSONObject(logs.snapshot()).toString();
	}

	public static void print(String extraInfo, Exception e) {
		if (e != null) {
			L.e(extraInfo + " : " + Log.getStackTraceString(e));
		} else {
			e(extraInfo + " : null exception");
		}
	}

	public static void d(Object anyObject) {
		L.d(new Gson().toJson(anyObject));
	}

}
