package com.example.clickntravel;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.fragments.ConfigurationFragment;
import com.example.handlers.FragmentHandler;
import com.example.utils.FragmentKey;
import com.example.clickntravel.FlightsDbAdapter;

public class MainActivity extends FragmentActivity implements
		SearchView.OnQueryTextListener, OnCloseListener {

	private SearchView mSearchView;
	private TextView mStatusView;
	private ListView mListView;
	private FlightsDbAdapter mDbHelper;
	private static final int GROUP_ID = 1;
	private static final int CONFIG_ID = 1;
	private ConfigurationFragment mPrefsFragment = null;
	private FragmentHandler fragmentHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

		ColorDrawable actionBarBackground = new ColorDrawable(Color.rgb(12,
				129, 199));
		ActionBar actionBar = getActionBar();

		setContentView(R.layout.activity_main);

		mStatusView = (TextView) findViewById(R.id.status_text);
		this.fragmentHandler = new FragmentHandler(getSupportFragmentManager());
		this.fragmentHandler.setFragment(FragmentKey.MAIN);
		actionBar.setBackgroundDrawable(actionBarBackground);
		actionBar.setIcon(R.drawable.back);

		mDbHelper = new FlightsDbAdapter(this);
		mDbHelper.open();

		// Clean all Customers
		mDbHelper.deleteAllFlights();

		mDbHelper.createFlights("U$S 1800", "Buenos Aires", "Los Angeles",
				"18/08/2013", "20/11/2013");
		mDbHelper.createFlights("U$S 1800", "Bueno", "Los Angeles",
				"18/08/2013", "20/11/2013");
		mDbHelper.createFlights("U$S 1800", "Bueni", "Los Angeles",
				"18/08/2013", "20/11/2013");
		mDbHelper.createFlights("U$S 1800", "Buej", "Los Angeles",
				"18/08/2013", "20/11/2013");
		mDbHelper.createFlights("U$S 1800", "Buem", "Los Angeles",
				"18/08/2013", "20/11/2013");
		mDbHelper.createFlights("U$S 1500", "Córdoba", "Roma", "18/08/2013",
				"20/11/2013");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.searchview_in_menu, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		mSearchView = (SearchView) searchItem.getActionView();
		mSearchView.setIconifiedByDefault(false); // Pone la lupita a la derecha
													// si se comenta
		mSearchView.setOnQueryTextListener(this);
		mSearchView.setOnCloseListener(this);
		mSearchView.setQueryHint("Búsqueda por destino");
		mListView = (ListView) findViewById(R.id.list);
		// setupSearchView(searchItem);

		menu.add(GROUP_ID, CONFIG_ID, CONFIG_ID,
				R.string.main_button_configuration);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		case CONFIG_ID:
			mPrefsFragment = new ConfigurationFragment();
			FragmentManager mFragmentManager = getFragmentManager();
			FragmentTransaction mFragmentTransaction = mFragmentManager
					.beginTransaction();
			mFragmentTransaction.replace(android.R.id.content, mPrefsFragment);
			mFragmentTransaction.commit();
			this.fragmentHandler.setFragment(FragmentKey.BASE);
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClickMyFlights(View view) {
		this.fragmentHandler.setFragment(FragmentKey.MY_FLIGHTS);
	}

	public void onClickAboutUs(View view) {
		this.fragmentHandler.setFragment(FragmentKey.ABOUT_US);
	}

	public void onClickMyDeals(View view) {
		this.fragmentHandler.setFragment(FragmentKey.MY_DEALS);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && mPrefsFragment != null) {
			mPrefsFragment.getPreferenceScreen().removeAll();
			mPrefsFragment = null;
			Log.d("debug", "lalalalalalalala");
		}
		return super.onKeyDown(keyCode, event);
	}

	// Search View

	public boolean onQueryTextChange(String newText) {
		// mStatusView.setText("Query = " + newText);
		showResults(newText + "*");
		return false;
	}

	public boolean onQueryTextSubmit(String query) {
		// mStatusView.setText("Query = " + query + " : submitted");
		showResults(query + "*");
		return false;
	}

	public boolean onClose() {
		mStatusView.setText("Closed!");
		return false;
	}

	protected boolean isAlwaysExpanded() {
		return false;
	}

	private void showResults(String query) {

		Cursor cursor = mDbHelper.searchFlights((query != null ? query
				.toString() : "@@@@"));

		if (cursor != null) {

			// Specify the columns we want to display in the result
			String[] from = new String[] { FlightsDbAdapter.KEY_PRICE,
					FlightsDbAdapter.KEY_FROM, FlightsDbAdapter.KEY_TO,
					FlightsDbAdapter.KEY_DEPDATE, FlightsDbAdapter.KEY_RETDATE };

			// Specify the Corresponding layout elements where we want the
			// columns to go
			int[] to = new int[] { R.id.scustomer, R.id.sname, R.id.scity,
					R.id.sstate, R.id.szipCode };

			// Create a simple cursor adapter for the definitions and apply them
			// to the ListView
			@SuppressWarnings("deprecation")
			SimpleCursorAdapter Flights = new SimpleCursorAdapter(this,
					R.layout.flightresult, cursor, from, to);
			mListView.setAdapter(Flights);

			// Define the on-click listener for the list items
			// mListView.setOnItemClickListener(new OnItemClickListener() {
			// public void onItemClick(AdapterView<?> parent, View view,
			// int position, long id) {
			// // Get the cursor, positioned to the corresponding row in
			// the result set
			// Cursor cursor = (Cursor)
			// mListView.getItemAtPosition(position);
			//
			// // Get the state's capital from this row in the database.
			// String customer =
			// cursor.getString(cursor.getColumnIndexOrThrow("customer"));
			// String name =
			// cursor.getString(cursor.getColumnIndexOrThrow("name"));
			// String city =
			// cursor.getString(cursor.getColumnIndexOrThrow("city"));
			// String state =
			// cursor.getString(cursor.getColumnIndexOrThrow("state"));
			// String zipCode =
			// cursor.getString(cursor.getColumnIndexOrThrow("zipCode"));
			//
			// //Check if the Layout already exists
			// LinearLayout customerLayout =
			// (LinearLayout)findViewById(R.id.customerLayout);
			// if(customerLayout == null){
			// //Inflate the Customer Information View
			// LinearLayout leftLayout =
			// (LinearLayout)findViewById(R.id.rightLayout);
			// View customerInfo =
			// getLayoutInflater().inflate(R.layout.flightinfo,
			// leftLayout, false);
			// leftLayout.addView(customerInfo);
			// }
			//
			// //Get References to the TextViews
			// priceText = (TextView) findViewById(R.id.customer);
			//
			// fromText = (TextView) findViewById(R.id.name);
			// toText = (TextView) findViewById(R.id.city);
			// depDateText = (TextView) findViewById(R.id.state);
			// retDateText = (TextView) findViewById(R.id.zipCode);
			//
			// // Update the parent class's TextView
			// priceText.setText(customer);
			// fromText.setText(name);
			// toText.setText(city);
			// depDateText.setText(state);
			// retDateText.setText(zipCode);
			//
			// searchView.setQuery("",true);
			// }
			// });
		}
	}

}
