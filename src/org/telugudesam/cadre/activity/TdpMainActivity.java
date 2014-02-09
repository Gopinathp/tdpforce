package org.telugudesam.cadre.activity;

import java.util.List;

import org.telugudesam.cadre.Config;
import org.telugudesam.cadre.R;
import org.telugudesam.cadre.database.DbUtils;
import org.telugudesam.cadre.fragments.DevelopmentCardsFragment;
import org.telugudesam.cadre.objects.Events;
import org.telugudesam.cadre.util.L;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import de.greenrobot.event.EventBus;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class TdpMainActivity extends BaseTdpActivity implements
		ActionBar.OnNavigationListener {

	private static final int FETCH_SIZE = 1000;
	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Crashlytics.start(this);
		ParseAnalytics.trackAppOpened(getIntent());
		setContentView(R.layout.activity_tdp_main);
		getActionBar().setDisplayHomeAsUpEnabled(false);

		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(
		// Specify a SpinnerAdapter to populate the dropdown list.
				new ArrayAdapter<String>(actionBar.getThemedContext(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, getResources().getStringArray(
								R.array.section_headings)), this);
		triggerCardsFetch();
	}

	private void triggerCardsFetch() {
		L.d("triggerCardsFetch");
		ParseQuery<ParseObject> query = ParseQuery.getQuery("DevelopmentCard");
		query.whereGreaterThan("updatedAt",
				DbUtils.getLatestDevCardRecordUpdatedAt());
		query.orderByAscending("updatedAt");
		query.setLimit(FETCH_SIZE);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> list, ParseException exception) {
				L.print(exception);
				if (exception == null) {
					if (list.size() > 0) {
						DbUtils.persistDevCards(list);
						EventBus.getDefault().post(new Events.RefreshCards());
						showNotification(list.size()
								+ " new cards have been downloaded or updated");
					} else {
						showNotification("No new cards found");
					}
					if (list.size() == FETCH_SIZE) {
						triggerCardsFetch();
					}
				}

			}
		});
	}

	private void showNotification(String msg) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Toast toast = Toast.makeText(this, "    " + msg + "    ",
					Toast.LENGTH_LONG);
			TextView view = (TextView) toast.getView().findViewById(
					android.R.id.message);
			view.setGravity(Gravity.CENTER);
			toast.show();
		} else {
			Crouton.makeText(TdpMainActivity.this, msg, Style.INFO).show();
		}
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tdp_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_counter) {
			launchCountDownActivity();
			return true;
		} else if (item.getItemId() == android.R.id.home) {
			return false;
		} else if (item.getItemId() == R.id.action_refresh) {
			triggerCardsFetch();
			return true;
		} else if (item.getItemId() == R.id.action_contact_us) {
			launchEmailClient();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void launchEmailClient() {
		/* Create the Intent */
		final Intent emailIntent = new Intent(
				android.content.Intent.ACTION_SEND);

		/* Fill it with Data */
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
				new String[] { Config.CONTACT_US_EMAIL });
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
				"Feedback on " + getAppName());

		/* Send it off to the Activity-Chooser */
		try {
			startActivity(Intent.createChooser(emailIntent, "Send mail..."));
		} catch (Exception e) {
		}

	}

	private String getAppName() {
		final PackageManager pm = getApplicationContext().getPackageManager();
		ApplicationInfo ai;
		try {
			ai = pm.getApplicationInfo(this.getPackageName(), 0);
		} catch (final NameNotFoundException e) {
			ai = null;
		}
		final String applicationName = (String) (ai != null ? pm
				.getApplicationLabel(ai) : "(unknown)");
		return applicationName;
	}

	private void launchCountDownActivity() {
		startActivity(new Intent(this, ConversionCounterActivity.class));
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// When the given dropdown item is selected, show its contents in the
		// container view.
		Fragment fragment = new DevelopmentCardsFragment();
		Bundle args = new Bundle();
		args.putInt(DevelopmentCardsFragment.ARG_SECTION_NUMBER, position);
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.layout_content, fragment).commit();
		return true;
	}

}
