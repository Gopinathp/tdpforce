package org.telugudesam.cadre.objects.dao;

import android.content.Context;
import com.turbomanage.storm.DatabaseHelper;
import com.turbomanage.storm.TableHelper;
import org.telugudesam.cadre.objects.DevelopmentCard;
import com.turbomanage.storm.SQLiteDao;
import com.turbomanage.storm.types.LongConverter;
import com.turbomanage.storm.types.StringConverter;

/**
 * GENERATED CODE
 *
 * @author David M. Chandler
 */
public class DevelopmentCardDao extends SQLiteDao<DevelopmentCard>{

    @Override
	public DatabaseHelper getDbHelper(Context ctx) {
		return org.telugudesam.cadre.database.Tdp_cadre_boxFactory.getDatabaseHelper(ctx);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public TableHelper getTableHelper() {
		return new org.telugudesam.cadre.objects.dao.DevelopmentCardTable();
	}

	/**
	 * @see SQLiteDao#SQLiteDao(Context)
	 */
	public DevelopmentCardDao(Context ctx) {
		super(ctx);
	}

}