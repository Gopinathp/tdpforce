package org.telugudesam.cadre.activity;

import org.telugudesam.cadre.util.L;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.readystatesoftware.systembartint.SystemBarTintManager;

public abstract class BaseTdpActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		L.d(this.getClass().getSimpleName() + " onCreate: ");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setTintAlpha(0f);
//		tintManager.setTintColor(Color.RED);
		tintManager.setStatusBarTintEnabled(true);
	    tintManager.setNavigationBarTintEnabled(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		L.d(this.getClass().getSimpleName() + " onResume: ");
	}

	@Override
	protected void onStop() {
		super.onStop();
		L.d(this.getClass().getSimpleName() + " onStop: ");
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		L.d(" onOptionsmenuitemselected");
		if(item.getItemId() == android.R.id.home) {
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStart() {
		super.onStart();
		L.d(this.getClass().getSimpleName() + " onStart: ");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		L.d(this.getClass().getSimpleName() + " onRestart: ");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		L.d(this.getClass().getSimpleName() + " onSaveInstanceState: ");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		L.d(this.getClass().getSimpleName() + " onRestoreInstanceState: ");
	}

}
