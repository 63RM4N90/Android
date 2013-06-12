package com.example.clickntravel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void swapToMyFlights(View cur_view) {
		Intent intent = new Intent(this, MyFlightsActivity.class);
		startActivity(intent);
	}

	public void swapToConfiguration(View cur_view) {
		Intent intent = new Intent(this, ConfigurationActivity.class);
		startActivity(intent);
	}

	public void swapToFlights(View cur_view) {
		Intent intent = new Intent(this, SearchableActivity.class);
		EditText editText = (EditText) findViewById(R.id.searchtmp);
		String message = editText.getText().toString();
		intent.putExtra("query", message);
		startActivity(intent);
	}
}
