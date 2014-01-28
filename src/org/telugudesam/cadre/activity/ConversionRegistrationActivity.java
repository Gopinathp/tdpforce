package org.telugudesam.cadre.activity;

import org.telugudesam.cadre.R;

import android.os.Bundle;
import android.view.Menu;

public class ConversionRegistrationActivity extends BaseTdpActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversion_registration);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.conversion_registration, menu);
		return true;
	}

}
