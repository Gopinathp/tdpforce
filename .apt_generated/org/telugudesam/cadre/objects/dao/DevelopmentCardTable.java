package org.telugudesam.cadre.objects.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import com.turbomanage.storm.query.FilterBuilder;
import com.turbomanage.storm.TableHelper;
import java.util.Map;
import java.util.HashMap;
import org.telugudesam.cadre.objects.DevelopmentCard;
import com.turbomanage.storm.SQLiteDao;
import com.turbomanage.storm.types.LongConverter;
import com.turbomanage.storm.types.StringConverter;

/**
 * GENERATED CODE
 *
 * This class contains the SQL DDL for the named entity / table.
 * These methods are not included in the EntityDao class because
 * they must be executed before the Dao can be instantiated, and
 * they are instance methods vs. static so that they can be
 * overridden in a typesafe manner.
 *
 * @author David M. Chandler
 */
public class DevelopmentCardTable extends TableHelper<DevelopmentCard> {

	public enum Columns implements TableHelper.Column {
		_id,
		NOTES,
		PICSARRAY,
		SECTIONSARRAY,
		SUBTITLE,
		TITLE
	}

	@Override
	public String getTableName() {
		return "DevelopmentCard";
	}

	@Override
	public Column[] getColumns() {
		return Columns.values();
	}
	
	@Override
	public long getId(DevelopmentCard obj) {
		return obj.getId();
	}
	
	@Override
	public void setId(DevelopmentCard obj, long id) {
		obj.setId(id);
	}

	@Override
	public Column getIdCol() {
		return Columns._id;
	}

	@Override
	public String createSql() {
		return
			"CREATE TABLE IF NOT EXISTS DevelopmentCard(" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
				"NOTES TEXT," +
				"PICSARRAY TEXT," +
				"SECTIONSARRAY TEXT," +
				"SUBTITLE TEXT," +
				"TITLE TEXT" +
			")";
	}

	@Override
	public String dropSql() {
		return "DROP TABLE IF EXISTS DevelopmentCard";
	}

	@Override
	public String upgradeSql(int oldVersion, int newVersion) {
		return null;
	}

	@Override
	public String[] getRowValues(Cursor c) {
		String[] values = new String[this.getColumns().length];
		String[] defaultValues = getDefaultValues();
		int colIdx; // entity field's position in the cursor
		colIdx = c.getColumnIndex("_id"); values[0] = (colIdx < 0) ? defaultValues[0] : LongConverter.GET.toString(getLongOrNull(c, colIdx));
		colIdx = c.getColumnIndex("NOTES"); values[1] = (colIdx < 0) ? defaultValues[1] : StringConverter.GET.toString(getStringOrNull(c, colIdx));
		colIdx = c.getColumnIndex("PICSARRAY"); values[2] = (colIdx < 0) ? defaultValues[2] : StringConverter.GET.toString(getStringOrNull(c, colIdx));
		colIdx = c.getColumnIndex("SECTIONSARRAY"); values[3] = (colIdx < 0) ? defaultValues[3] : StringConverter.GET.toString(getStringOrNull(c, colIdx));
		colIdx = c.getColumnIndex("SUBTITLE"); values[4] = (colIdx < 0) ? defaultValues[4] : StringConverter.GET.toString(getStringOrNull(c, colIdx));
		colIdx = c.getColumnIndex("TITLE"); values[5] = (colIdx < 0) ? defaultValues[5] : StringConverter.GET.toString(getStringOrNull(c, colIdx));
		return values;
	}

	@Override
	public void bindRowValues(InsertHelper insHelper, String[] rowValues) {
		if (rowValues[0] == null) insHelper.bindNull(1); else insHelper.bind(1, LongConverter.GET.fromString(rowValues[0]));
		if (rowValues[1] == null) insHelper.bindNull(2); else insHelper.bind(2, StringConverter.GET.fromString(rowValues[1]));
		if (rowValues[2] == null) insHelper.bindNull(3); else insHelper.bind(3, StringConverter.GET.fromString(rowValues[2]));
		if (rowValues[3] == null) insHelper.bindNull(4); else insHelper.bind(4, StringConverter.GET.fromString(rowValues[3]));
		if (rowValues[4] == null) insHelper.bindNull(5); else insHelper.bind(5, StringConverter.GET.fromString(rowValues[4]));
		if (rowValues[5] == null) insHelper.bindNull(6); else insHelper.bind(6, StringConverter.GET.fromString(rowValues[5]));
	}

	@Override
	public String[] getDefaultValues() {
		String[] values = new String[getColumns().length];
		DevelopmentCard defaultObj = new DevelopmentCard();
		values[0] = LongConverter.GET.toString(LongConverter.GET.toSql(defaultObj.getId()));
		values[1] = StringConverter.GET.toString(StringConverter.GET.toSql(defaultObj.getNotes()));
		values[2] = StringConverter.GET.toString(StringConverter.GET.toSql(defaultObj.getPicsArray()));
		values[3] = StringConverter.GET.toString(StringConverter.GET.toSql(defaultObj.getSectionsArray()));
		values[4] = StringConverter.GET.toString(StringConverter.GET.toSql(defaultObj.getSubTitle()));
		values[5] = StringConverter.GET.toString(StringConverter.GET.toSql(defaultObj.getTitle()));
		return values;
	}

	@Override
	public DevelopmentCard newInstance(Cursor c) {
		DevelopmentCard obj = new DevelopmentCard();
		obj.setId(c.getLong(0));
		obj.setNotes(c.getString(1));
		obj.setPicsArray(c.getString(2));
		obj.setSectionsArray(c.getString(3));
		obj.setSubTitle(c.getString(4));
		obj.setTitle(c.getString(5));
		return obj;
	}

	@Override
	public ContentValues getEditableValues(DevelopmentCard obj) {
		ContentValues cv = new ContentValues();
		cv.put("_id", obj.getId());
		cv.put("NOTES", obj.getNotes());
		cv.put("PICSARRAY", obj.getPicsArray());
		cv.put("SECTIONSARRAY", obj.getSectionsArray());
		cv.put("SUBTITLE", obj.getSubTitle());
		cv.put("TITLE", obj.getTitle());
		return cv;
	}

	@Override
	public FilterBuilder buildFilter(FilterBuilder filter, DevelopmentCard obj) {
		DevelopmentCard defaultObj = new DevelopmentCard();
		// Include fields in query if they differ from the default object
		if (obj.getId() != defaultObj.getId())
			filter = filter.eq(Columns._id, LongConverter.GET.toSql(obj.getId()));
		if (obj.getNotes() != defaultObj.getNotes())
			filter = filter.eq(Columns.NOTES, StringConverter.GET.toSql(obj.getNotes()));
		if (obj.getPicsArray() != defaultObj.getPicsArray())
			filter = filter.eq(Columns.PICSARRAY, StringConverter.GET.toSql(obj.getPicsArray()));
		if (obj.getSectionsArray() != defaultObj.getSectionsArray())
			filter = filter.eq(Columns.SECTIONSARRAY, StringConverter.GET.toSql(obj.getSectionsArray()));
		if (obj.getSubTitle() != defaultObj.getSubTitle())
			filter = filter.eq(Columns.SUBTITLE, StringConverter.GET.toSql(obj.getSubTitle()));
		if (obj.getTitle() != defaultObj.getTitle())
			filter = filter.eq(Columns.TITLE, StringConverter.GET.toSql(obj.getTitle()));
		return filter;
	}

}