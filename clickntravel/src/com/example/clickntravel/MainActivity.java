package com.example.clickntravel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.api.ApiIntent;
import com.example.api.ApiResultReceiver;
import com.example.api.Callback;
import com.example.fragments.ConfigurationFragment;
import com.example.handlers.FragmentHandler;
import com.example.utils.City;
import com.example.utils.FragmentKey;

public class MainActivity extends FragmentActivity implements
		SearchView.OnQueryTextListener, OnCloseListener {

	private SearchView mSearchView;
	private TextView mStatusView;
	private ListView mListView;
	private static final int CONFIG_ID = 1;
	private FlightsDbAdapter mDbHelper;
	private static final int GROUP_ID = 1;
	private ConfigurationFragment mPrefsFragment = null;
	private FragmentHandler fragmentHandler;

	private Map<String, City> citiesMap;

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

		mDbHelper.deleteAllFlights();

		createCities();
	}

	private void createCities() {

		citiesMap = new HashMap<String, City>();

		Callback callback = new Callback() {
			public void handleResponse(JSONObject response) {
				try {
					JSONArray cityArray = response.getJSONArray("cities");
					List<String> citiesList = new LinkedList<String>();

					for (int i = 0; i < cityArray.length(); i++) {
						String name = cityArray.getJSONObject(i).optString(
								"name");
						String id = cityArray.getJSONObject(i).optString(
								"cityId");
						mDbHelper.createFlights("", "", name, "", "");
						citiesMap.put(id, new City(id, name));
						citiesList.add(name);
					}

				} catch (JSONException e) {
				}
			}

		};

		ApiResultReceiver receiver = new ApiResultReceiver(new Handler(),
				callback);
		ApiIntent intent = new ApiIntent("GetCities", "Geo", receiver, this);
		intent.setParams(new LinkedList<NameValuePair>());
		this.startService(intent);

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
		mListView = (ListView) findViewById(R.id.list);
		
		// Setea el color del textito de la search view
        String text = "<font color = #DDDDDD>" + "Búsqueda por destino" + "</font>";
        mSearchView.setQueryHint(Html.fromHtml(text));

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

	public boolean onQueryTextChange(String newText) {
		showResults(newText + "*");
		return false;
	}

	public boolean onQueryTextSubmit(String query) {
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
			int[] to = new int[] { 0, 0, R.id.scity, 0, 0 };

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
