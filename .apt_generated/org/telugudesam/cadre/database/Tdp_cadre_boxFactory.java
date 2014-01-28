package org.telugudesam.cadre.database;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import com.turbomanage.storm.DatabaseHelper;
import com.turbomanage.storm.TableHelper;
import com.turbomanage.storm.api.DatabaseFactory;

/**
 * GENERATED CODE
 *
 * Provides a singleton instance of the {@link DatabaseHelper} and
 * holds the properties obtained from @Database and @Entity annotations
 * (name, version, and associated tables).
 *
 * @author David M. Chandler
 */
public class Tdp_cadre_boxFactory implements DatabaseFactory {

	private static final String DB_NAME = "tdp_cadre_box";
	private static final int DB_VERSION = 1;
	private static final TableHelper[] TABLE_HELPERS = new TableHelper[] {
		new org.telugudesam.cadre.objects.dao.DevelopmentCardTable()
	};
	private static DatabaseHelper mInstance;

	/**
	 * Provides a singleton instance of the DatabaseHelper per application
	 * to prevent threading issues. See
	 * https://github.com/commonsguy/cwac-loaderex#notes-on-threading
	 *
	 * @param ctx Application context
	 * @return {@link SQLiteOpenHelper} instance
	 */
	public static DatabaseHelper getDatabaseHelper(Context ctx) {
		if (mInstance==null) {
			// in case this is called from an AsyncTask or other thread
		    synchronized(Tdp_cadre_boxFactory.class) {
		    		if (mInstance == null)
					mInstance = new org.telugudesam.cadre.database.DbHelper(
									ctx.getApplicationContext(),
									new Tdp_cadre_boxFactory());
			}
		}
		return mInstance;
	}

	public String getName() {
		return DB_NAME;
	}

	public int getVersion() {
		return DB_VERSION;
	}

	public TableHelper[] getTableHelpers() {
		return TABLE_HELPERS;
	}

	private Tdp_cadre_boxFactory() {
		// non-instantiable
	}

}
