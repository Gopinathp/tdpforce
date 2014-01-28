package org.telugudesam.cadre.activity;

import java.util.Arrays;

import org.telugudesam.cadre.App;
import org.telugudesam.cadre.R;
import org.telugudesam.cadre.util.L;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class ConversionCounterActivity extends BaseTdpActivity implements
		OnClickListener {

	private EditText targetEditText;
	private View converionView;
	private View getStartedView;
	private ProfilePictureView pictureView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversion_counter);
		findViewById(R.id.registerAConversionButton).setOnClickListener(this);
		findViewById(R.id.login_with_facebook_button).setOnClickListener(this);
		converionView = findViewById(R.id.layout_conversion);
		getStartedView = findViewById(R.id.layout_get_started);
		pictureView = (ProfilePictureView) findViewById(R.id.selection_profile_pic);
	}

	private void updateView(ParseUser user) {
		L.d("updateview");
		if (user == null)
			user = ParseUser.getCurrentUser();
		if (user != null && user.isAuthenticated()) {
			L.d("Enabling counter views");
			findViewById(R.id.container).post(new Runnable() {
				public void run() {
					getStartedView.setVisibility(View.GONE);
					converionView.setVisibility(View.VISIBLE);
					setTexts();
				}
			});

		} else {
			L.d("Disabling counter views. Showing getting started views");
			findViewById(R.id.container).post(new Runnable() {
				public void run() {
					converionView.setVisibility(View.GONE);
					getStartedView.setVisibility(View.VISIBLE);
				}
			});
		}
	}

	protected void setTexts() {
		findViewById(R.id.thank_you_textview).setVisibility(View.VISIBLE);
		//((TextView)findViewById(R.id.gear_up_textview)).setText(getString(R.string.gear_up_msg, formatArgs));
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateView(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.conversion_counter, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_change_conversion_target) {
			handleConversionTargetChange();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void handleConversionTargetChange() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Change your target for elections 2014");
		android.content.DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					if (!handleTargetChange())
						break;
				case DialogInterface.BUTTON_NEGATIVE:
					dialog.dismiss();
					break;

				default:
					break;
				}

			}
		};
		builder.setPositiveButton("Change", listener);
		builder.setNegativeButton(android.R.string.cancel, listener);
		View view = LayoutInflater.from(this).inflate(
				R.layout.dialog_change_target, null);
		builder.setView(view);
		AlertDialog dialog = builder.create();
		targetEditText = (EditText) view.findViewById(R.id.targetEditText);
		targetEditText.setText(String.valueOf(App.getConversionTarget()));
		dialog.show();

	}

	protected boolean handleTargetChange() {
		String newTargetStr = targetEditText.getText().toString();
		try {
			int newTarget = Integer.parseInt(newTargetStr);
			App.setNewConversionTarget(newTarget);
			return true;
		} catch (Exception e) {
			Crouton.makeText(ConversionCounterActivity.this,
					"Enter a valid number", Style.ALERT).show();
			L.print(e);
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.registerAConversionButton:
			launchConversionRegistrationScreen();
			break;
		case R.id.login_with_facebook_button:
			loginWithFacebook();
			break;

		default:
			break;
		}
	}

	private void loginWithFacebook() {
		ParseFacebookUtils.logIn(Arrays.asList("email"), this,
				new LogInCallback() {

					@Override
					public void done(ParseUser user, ParseException exception) {
						L.print(exception);
						if (user != null && user.isAuthenticated()) {
							updateView(user);
							updateUserEmailAddress();
						}

					}
				});
	}

	protected void updateUserEmailAddress() {
		L.d("updateUserEmailAddress");
		Request.newMeRequest(Session.getActiveSession(),
				new GraphUserCallback() {

					@Override
					public void onCompleted(GraphUser user, Response response) {
						L.d("onCompleted");
						if (user != null) {
							App.saveFbUser(user);
							String email = (String) user.getProperty("email");
							pictureView.setProfileId(user.getId());
							L.d("user email id = " + email);
							ParseUser currentUser = ParseUser.getCurrentUser();
							currentUser.setEmail(email);
							currentUser.saveEventually();
						}
					}
				}).executeAsync();

	}

	private void launchConversionRegistrationScreen() {
		startActivity(new Intent(this, ConversionRegistrationActivity.class));
	}

}
