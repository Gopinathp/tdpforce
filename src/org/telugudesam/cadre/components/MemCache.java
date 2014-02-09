package org.telugudesam.cadre.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.telugudesam.cadre.App;
import org.telugudesam.cadre.objects.DevelopmentCard;
import org.telugudesam.cadre.objects.Section;
import org.telugudesam.cadre.objects.dao.DevelopmentCardDao;

import android.database.Cursor;

public class MemCache {
	private static ArrayList<DevelopmentCard> yTDPdevCards;
	private static ArrayList<DevelopmentCard> yNotCongressdevCards;
	private static ArrayList<DevelopmentCard> yNotYsrcpdevCards;
	private static ArrayList<DevelopmentCard> yNotLoksattadevCards;
	private static ArrayList<DevelopmentCard> yNotAapdevCards;
	private static ArrayList<DevelopmentCard> yNotTrsdevCards;

	public static ArrayList<DevelopmentCard> getDevelopmentCards(Section section) {
		switch (section) {
		case WHY_TDP:
			return getTdpCards();
		case WHY_NOT_CONGRESS:
			return getCongressCards();
		case WHY_NOT_YSRCP:
			return getYsrcpCards();
		case WHY_NOT_LOKSATTA:
			return getLoksattaCards();
		case WHY_NOT_AAP:
			return getAapCards();
		case WHY_NOT_TRS:
			return getTrsCards();
		default:
			break;
		}
		return null;
	}

	private static ArrayList<DevelopmentCard> getTrsCards() {
		if (yNotTrsdevCards == null) {
			yNotTrsdevCards = (ArrayList<DevelopmentCard>) loadCards(Section.WHY_NOT_TRS);
		}
		return yNotTrsdevCards;
	}

	private static ArrayList<DevelopmentCard> getAapCards() {
		if (yNotAapdevCards == null) {
			yNotAapdevCards = (ArrayList<DevelopmentCard>) loadCards(Section.WHY_NOT_AAP);
		}
		return yNotAapdevCards;
	}

	private static ArrayList<DevelopmentCard> getLoksattaCards() {
		if (yNotLoksattadevCards == null) {
			yNotLoksattadevCards = (ArrayList<DevelopmentCard>) loadCards(Section.WHY_NOT_LOKSATTA);
		}
		return yNotLoksattadevCards;
	}

	private static ArrayList<DevelopmentCard> getYsrcpCards() {
		if (yNotYsrcpdevCards == null) {
			yNotYsrcpdevCards = (ArrayList<DevelopmentCard>) loadCards(Section.WHY_NOT_YSRCP);
		}
		return yNotYsrcpdevCards;
	}

	private static ArrayList<DevelopmentCard> getCongressCards() {
		if (yNotCongressdevCards == null) {
			yNotCongressdevCards = (ArrayList<DevelopmentCard>) loadCards(Section.WHY_NOT_CONGRESS);
		}
		return yNotCongressdevCards;
	}

	private static ArrayList<DevelopmentCard> getTdpCards() {
		if (yTDPdevCards == null) {
			yTDPdevCards = (ArrayList<DevelopmentCard>) loadCards(Section.WHY_TDP);
		}
		return yTDPdevCards;
	}

	private static List<DevelopmentCard> loadCards(Section section) {
		DevelopmentCardDao cardDao = new DevelopmentCardDao(App.getContext());
		Cursor cursor = cardDao.query(
				"SECTIONSARRAY LIKE '%" + section.ordinal()
						+ "%' and isDeleted=0 ", null);
		List<DevelopmentCard> list = cardDao.asList(cursor);
		Collections.sort(list);
		return list;
	}

	public static void resetDevelopmentCards() {
		yTDPdevCards = null;
		yNotYsrcpdevCards = null;
		yNotCongressdevCards = null;
		yNotLoksattadevCards = null;
		yNotAapdevCards = null;
		yNotTrsdevCards = null;
	}

}
