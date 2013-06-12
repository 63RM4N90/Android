package com.example.clickntravel;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MyFlightsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_flights);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_flights, menu);
		return true;
	}

}
