package org.telugudesam.cadre.database;

import android.content.Context;

import com.turbomanage.storm.DatabaseHelper;
import com.turbomanage.storm.api.Database;
import com.turbomanage.storm.api.DatabaseFactory;

@Database(name = DbHelper.dbName, version = DbHelper.version)
public class DbHelper extends DatabaseHelper {
	public static final String dbName = "tdp_cadre_box";
	public static final int version = 1;

	public DbHelper(Context ctx, DatabaseFactory dbFactory) {
		super(ctx, dbFactory);
	}

	@Override
	public UpgradeStrategy getUpgradeStrategy() {
		return UpgradeStrategy.BACKUP_RESTORE;
	}

}
