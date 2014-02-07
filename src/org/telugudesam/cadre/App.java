package org.telugudesam.cadre;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.json.JSONArray;
import org.telugudesam.cadre.activity.TdpMainActivity;
import org.telugudesam.cadre.objects.DevelopmentCard;
import org.telugudesam.cadre.objects.Section;
import org.telugudesam.cadre.objects.dao.DevelopmentCardDao;
import org.telugudesam.cadre.util.L;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.ViewConfiguration;

import com.facebook.model.GraphUser;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.PushService;

public class App extends Application implements UncaughtExceptionHandler {

	private static App appInstance;
	private static SharedPreferences sharedPreferences;

	public static App getApp() {
		return appInstance;
	}

	public static Context getContext() {
		return appInstance.getApplicationContext();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		appInstance = this;
		Thread.setDefaultUncaughtExceptionHandler(this);
		initParse();
		sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
		forceTheOverflowMenuForDevicesWithHardMenuButton();
	}

	private void forceTheOverflowMenuForDevicesWithHardMenuButton() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");

			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			// presumably, not relevant
		}

	}

	private void initParse() {
		Parse.initialize(this, Config.PARSE_APP_ID, Config.PARSE_CLIENT_KEY);
		ParseFacebookUtils.initialize(Config.FACEBOOK_ID);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		PushService.setDefaultPushCallback(this, TdpMainActivity.class);
	}

	private void generateDummyData() {
		ArrayList<DevelopmentCard> cards = new ArrayList<DevelopmentCard>();
		cards.add(createTDPCard());
		cards.add(createTDPCard());
		cards.add(createTDPCard());
		cards.add(createTDPCard());
		cards.add(createTDPCard());
		cards.add(createTDPCard());
		cards.add(createTDPCard());
		cards.add(createYSRCPCard());
		cards.add(createYSRCPCard());
		cards.add(createCongressCard());

		DevelopmentCardDao dao = new DevelopmentCardDao(this);
		dao.insertMany(cards);

		ArrayList<ParseObject> objects = new ArrayList<ParseObject>();
		for (DevelopmentCard developmentCard : cards) {
			objects.add(developmentCard.toParseObject());
		}
		ParseObject.saveAllInBackground(objects);
	}

	private DevelopmentCard createCongressCard() {
		final DevelopmentCard card = new DevelopmentCard();
		final JSONArray jsonArray = new JSONArray();
		card.setPicsArray(jsonArray.toString());
		jsonArray.put(Section.WHY_NOT_CONGRESS.ordinal());
		card.setSectionsArray(jsonArray.toString());
		card.setTitle("An Anti-congress card");
		card.setSubTitle("A non congress subtitle");
		card.setNotes("some notes");
		return card;
		// card.setId(++id);
	}

	private DevelopmentCard createYSRCPCard() {
		final DevelopmentCard card = new DevelopmentCard();
		final JSONArray jsonArray = new JSONArray();
		card.setPicsArray(jsonArray.toString());
		jsonArray.put(Section.WHY_NOT_YSRCP.ordinal());
		card.setSectionsArray(jsonArray.toString());
		card.setTitle("An Anti-ysrcp card");
		card.setSubTitle("A non ysrcp subtitle");
		card.setNotes("some notes");
		return card;
		// card.setId(++id);
	}

	int id = 0;

	private DevelopmentCard createTDPCard() {
		final DevelopmentCard card = new DevelopmentCard();
		final JSONArray jsonArray = new JSONArray();
		card.setPicsArray(jsonArray.toString());
		jsonArray.put(Section.WHY_TDP.ordinal());
		card.setSectionsArray(jsonArray.toString());
		card.setTitle("A TDP card");
		card.setSubTitle("A TDP card subtitle");
		card.setNotes("some notes");
		return card;
		// card.setId(++id);
	}

	public static void setNewConversionTarget(int newTarget) {
		sharedPreferences.edit().putInt("conversion_target", newTarget).apply();
	}

	public static int getConversionTarget() {
		return sharedPreferences.getInt("conversion_target",
				Config.DEFAULT_CONVERSION_COUNT);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		L.d("Exception in thread: " + thread.getName());
		L.print(new Exception(ex));

	}

	public static void saveFbUser(GraphUser user) {
		// appInstance.

	}

}
