package com.example.fragments;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.api.ApiIntent;
import com.example.api.ApiResultReceiver;
import com.example.api.Callback;
import com.example.clickntravel.R;
import com.example.handlers.FragmentHandler;
import com.example.utils.City;
import com.example.utils.FlightsDbAdapter;
import com.example.utils.FragmentKey;

public class MainFragment extends Fragment implements
		SearchView.OnQueryTextListener, OnCloseListener {

	private SearchView mSearchView;
	private TextView mStatusView;
	private ListView mListView;
	private View view;

	public static FlightsDbAdapter mDbHelper;
	private Map<String, City> citiesMap;

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActivity().getActionBar();

		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);

		setHasOptionsMenu(true);

		mDbHelper = new FlightsDbAdapter(this.getActivity());
		
		mDbHelper = mDbHelper.open();

		mDbHelper.deleteAllFlights();

		view = inflater.inflate(R.layout.main_fragment, container, false);
		
		mListView = (ListView) view.findViewById(R.id.dealList);
		
		createCities();
		
		return view;
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		inflater.inflate(R.menu.searchview_in_menu, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		mSearchView = (SearchView) searchItem.getActionView();
		mSearchView.setIconifiedByDefault(false); // Pone la lupita a la derecha si se comenta
		mSearchView.setOnQueryTextListener((OnQueryTextListener) this);
		mSearchView.setOnCloseListener((OnCloseListener) this);
		
		// Setea el color del textito de la search view
		String text = "<font color = #DDDDDD>" + getString(R.string.search_view_text) + "</font>";
		mSearchView.setQueryHint(Html.fromHtml(text));
	}

	private void createCities() {
		citiesMap = new HashMap<String, City>();
		Callback callback = new Callback() {

			public void handleResponse(JSONObject response) {
				try {
					JSONArray cityArray = response.getJSONArray("cities");
					for (int i = 0; i < cityArray.length(); i++) {
						String name = cityArray.getJSONObject(i).optString("name");
						String id = cityArray.getJSONObject(i).optString("cityId");
						addFlight(name, id);
					}
				} catch (JSONException e) {	}
			}

			private void addFlight(String name, String id) {
				
				if (name.contains("Buenos Aires")) {
					
					return;
				}

				if (name.contains("Barcelona")) {
					
					name = "Barcelona, España";
					
				} else if (name.contains("Madrid")) {
					
					name = "Madrid, Comunidad de Madrid, España";
				}

				citiesMap.put(name, new City(id, name));
				mDbHelper.createFlights(name);
			}

		};

		ApiResultReceiver receiver = new ApiResultReceiver(new Handler(), callback);
		ApiIntent intent = new ApiIntent("GetCities", "Geo", receiver, this.getActivity());
		intent.setParams(new LinkedList<NameValuePair>());
		this.getActivity().startService(intent);

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
		if (query == null)
			return;

		Cursor cursor = mDbHelper.searchFlights(query);
		if (cursor != null) {

			// Specify the columns we want to display in the result
			String[] from = new String[] { FlightsDbAdapter.KEY_TO };

			// Specify the Corresponding layout elements where we want the
			// columns to go
			int[] to = new int[] { R.id.to };

			// Create a simple cursor adapter for the definitions and apply them
			// to the ListView
			
			SimpleCursorAdapter Flights = new SimpleCursorAdapter(this.getActivity(), R.layout.flightresult, cursor, from, to);
			mListView.setAdapter(Flights);

			// Define the on-click listener for the list items
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					// Get the cursor, positioned to the corresponding row in
					// the result set
					Cursor cursor = (Cursor) mListView.getItemAtPosition(position);
					
					// Get the city from this row in the database
					String name = cursor.getString(cursor.getColumnIndexOrThrow("toCity"));

					City city = citiesMap.get(name);

					Bundle resultSearchBundle = new Bundle();
					resultSearchBundle.putString("cityId", city.getId());
					resultSearchBundle.putString("cityName", city.getName());
					
					FragmentHandler fragmentHandler = new FragmentHandler(getFragmentManager());

					fragmentHandler.setFragment(FragmentKey.SEARCH_DEALS_LIST, resultSearchBundle);
				}
			});
		}
	}
}