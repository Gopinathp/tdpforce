package org.telugudesam.cadre.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.telugudesam.cadre.App;
import org.telugudesam.cadre.components.MemCache;
import org.telugudesam.cadre.objects.DevelopmentCard;
import org.telugudesam.cadre.objects.dao.DevelopmentCardDao;
import org.telugudesam.cadre.util.L;

import android.database.Cursor;

import com.parse.ParseObject;

public class DbUtils {

	public static Date getLatestDevCardRecordUpdatedAt() {
		DevelopmentCardDao dao = new DevelopmentCardDao(App.getContext());
		Cursor cursor = dao.getDbHelper(App.getContext()).getReadableDatabase()
				.rawQuery("SELECT max(UPDATEDAT) from DevelopmentCard", null);
		long timeStamp = 0;
		if (cursor.moveToNext()) {
			timeStamp = cursor.getLong(0);
		}
		L.d("timestamp = " + timeStamp);
		return new Date(timeStamp);
	}

	public static void persistDevCards(List<ParseObject> list) {
		ArrayList<DevelopmentCard> cards = new ArrayList<DevelopmentCard>(
				list.size());
		for (ParseObject parseObject : list) {
			cards.add(DevelopmentCard.fromParseObject(parseObject));
		}
		L.d("Saving downloaded cards: " + cards.size());
		DevelopmentCardDao dao = new DevelopmentCardDao(App.getContext());
		for (DevelopmentCard developmentCard : cards) {
			L.d("Inserting card: " + developmentCard);
			try {
				dao.insert(developmentCard);
			} catch (Throwable ex) {
				L.d("Updating card: " + developmentCard);
				L.print(new Exception(ex));
				dao.update(developmentCard);
			}
		}
		// try {
		// dao.insertMany(cards);
		// } catch(SQLiteConstraintException e) {
		// L.print(e);
		// for (DevelopmentCard developmentCard : cards) {
		// L.d("Inserting card: " + developmentCard);
		// try {
		// dao.insert(developmentCard);
		// } catch(Exception ex) {
		// L.d("Updating card: " + developmentCard);
		// L.print(ex);
		// dao.update(developmentCard);
		// }
		// }
		//
		// }
		MemCache.resetDevelopmentCards();
	}

}
