package com.example.fragments;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.api.ApiIntent;
import com.example.api.ApiResultReceiver;
import com.example.api.Callback;
import com.example.clickntravel.R;
import com.example.utils.Deal;
import com.example.utils.FlightsDbAdapter;
import com.example.utils.MyDate;

public class ResultsSearchFragment extends Fragment {

	private List<String> dealPrices = new ArrayList<String>();
	private List<Deal> dealsList = new ArrayList<Deal>();

	private String nameFrom;
	private String idFrom = "BUE";
	private String nameTo;
	private String idTo;

	private Deal currentDeal;

	private ListView mListView;
	private FlightsDbAdapter mDbHelper;

	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ActionBar actionBar = getActivity().getActionBar();

		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.resultssearch_abs_layout);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		Bundle arguments = getArguments();

		idTo = arguments.getString("cityId");
		nameTo = arguments.getString("cityName");

		createDeals();

		if (view == null)
			view = inflater.inflate(R.layout.deal_list_fragment, container,
					false);

		mListView = (ListView) view.findViewById(R.id.deals_list_view);

		setHasOptionsMenu(true);

		mDbHelper = new FlightsDbAdapter(this.getActivity());

		mDbHelper = mDbHelper.open();

		mDbHelper.deleteAllFlights();

		return view;
	}

	public void createDeals() {

		Callback callback = new Callback() {

			public void handleResponse(JSONObject response) {
				try {
					JSONArray dealArray = response.getJSONArray("deals");
					for (int i = 0; i < dealArray.length(); i++) {
						String price = dealArray.getJSONObject(i).optString(
								"price");
						String id = dealArray.getJSONObject(i).optString(
								"cityId");
						getDealPrices(id, price);
					}

					getFlights();
				} catch (JSONException e) {
				}
			}

			private void getDealPrices(String id, String price) {
				if (id.equals(idTo))
					dealPrices.add(price);
			}

			private void getFlights() {

				Callback callback = new Callback() {

					public void handleResponse(JSONObject response) {
						try {
							JSONArray dealArray = response
									.getJSONArray("flights");
							String minPrice = null;
							int indexMinPrice = 0;

							for (int i = 0; i < dealArray.length(); i++) {
								String price = dealArray.getJSONObject(i)
										.getJSONObject("price")
										.getJSONObject("total")
										.optString("total");

								if (dealPrices.contains(price)) {

									addDeal(dealPrices.indexOf(price), price,
											dealArray);

								} else if (minPrice == null
										|| Double.valueOf(minPrice) > Double
												.valueOf(price)) {

									minPrice = price;
									indexMinPrice = i;
								}
							}

							if (dealsList.isEmpty()) {

								addDeal(indexMinPrice, minPrice, dealArray);
							}

						} catch (JSONException e) {
						}

						if (dealsList.isEmpty()) {

							Toast toast = Toast.makeText(getActivity(),
									R.string.no_deals_found, Toast.LENGTH_LONG);

							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						}

						showResults(nameTo + "*");
					}

					private void addDeal(int index, String price,
							JSONArray dealArray) {
						try {
							JSONObject obj = dealArray.getJSONObject(index);
							JSONObject segments = obj
									.getJSONArray("outboundRoutes")
									.getJSONObject(0).getJSONArray("segments")
									.getJSONObject(0);

							String airlineId = segments.optString("airlineId");
							String flightId = segments.optString("flightId");
							String flightNumber = segments
									.optString("flightNumber");
							String depTime = segments
									.getJSONObject("departure").optString(
											"date");
							String arrivalTime = segments.getJSONObject(
									"arrival").optString("date");

							nameFrom = segments.getJSONObject("departure")
									.optString("cityName");

							Deal curr = new Deal(idFrom, getCity(nameFrom),
									idTo, getCity(nameTo),
									getFloorPrice(price), airlineId, flightId,
									flightNumber, MyDate.convertDate(depTime),
									MyDate.convertDate(arrivalTime));

							dealsList.add(curr);

							mDbHelper.createFlights(curr.getPrice(),
									curr.getNameFrom(), curr.getNameTo(),
									curr.getDepTime(), curr.getArrivalTime(),
									curr.getAirlineId());

						} catch (JSONException e) {
						}
					}

					private String getFloorPrice(String price) {
						return "U$S " + price.split("\\.")[0];
					}
				};

				ApiResultReceiver receiver = new ApiResultReceiver(
						new Handler(), callback);
				ApiIntent intent = new ApiIntent("GetOneWayFlights", "Booking",
						receiver, getActivity());

				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

				nameValuePair.add(new BasicNameValuePair("from", idFrom));
				nameValuePair.add(new BasicNameValuePair("to", idTo));
				nameValuePair
						.add(new BasicNameValuePair("dep_date", getDate()));
				nameValuePair.add(new BasicNameValuePair("adults", "1"));
				nameValuePair.add(new BasicNameValuePair("children", "0"));
				nameValuePair.add(new BasicNameValuePair("infants", "0"));

				intent.setParams(nameValuePair);
				getActivity().startService(intent);
			}

			private String getDate() {

				Date date = new Date();

				Format formatter = new SimpleDateFormat("yyyy-MM-dddd");
				SimpleDateFormat simpleFormatter = new SimpleDateFormat(
						"yyyy-MM-dd");

				String dateString = formatter.format(date);
				Calendar calendar = Calendar.getInstance();

				try {
					calendar.setTime(simpleFormatter.parse(dateString));
				} catch (ParseException e) {
				}
				calendar.add(Calendar.DATE, 2); // number of days to add
				dateString = simpleFormatter.format(calendar.getTime());

				return dateString;
			}
		};

		ApiResultReceiver receiver = new ApiResultReceiver(new Handler(),
				callback);
		ApiIntent intent = new ApiIntent("GetFlightDeals2", "Booking",
				receiver, this.getActivity());

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("from", idFrom));
		intent.setParams(nameValuePair);
		this.getActivity().startService(intent);
	}

	private void showResults(String query) {

		if (query == null)
			return;

		Cursor cursor = mDbHelper.searchFlights(getCity(query));

		if (cursor != null) {

			// Specify the columns we want to display in the result
			String[] from = new String[] { FlightsDbAdapter.KEY_PRICE,
					FlightsDbAdapter.KEY_FROM, FlightsDbAdapter.KEY_TO,
					FlightsDbAdapter.KEY_DEPDATE, FlightsDbAdapter.KEY_RETDATE,
					FlightsDbAdapter.KEY_IMG };

			// Specify the Corresponding layout elements where we want the
			// columns to go
			int[] to = new int[] { R.id.price, R.id.from, R.id.to,
					R.id.depDate, R.id.retDate, R.id.airline_image_deal };

			// Create a simple cursor adapter for the definitions and apply them
			// to the ListView
			@SuppressWarnings("deprecation")
			SimpleCursorAdapter Flights = new SimpleCursorAdapter(
					this.getActivity(), R.layout.dealresult, cursor, from, to);
			mListView.setAdapter(Flights);

		
			
			// Define the on-click listener for the list items
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					// Get the cursor, positioned to the corresponding row in
					// the result set
					Cursor cursor = (Cursor) mListView
							.getItemAtPosition(position);

					// Get the city from this row in the database
					String price = cursor.getString(cursor
							.getColumnIndexOrThrow("price"));
					String from = cursor.getString(cursor
							.getColumnIndexOrThrow("fromCity"));
					String to = cursor.getString(cursor
							.getColumnIndexOrThrow("toCity"));
					String depDate = cursor.getString(cursor
							.getColumnIndexOrThrow("depDate"));
					String retDate = cursor.getString(cursor
							.getColumnIndexOrThrow("retDate"));
					String airlineId = cursor.getString(cursor
							.getColumnIndexOrThrow("img"));

					Deal newDeal = new Deal(from, to, depDate, retDate, price, airlineId);
					
					ImageView button_switch = (ImageView) view.findViewById(R.id.button_switch);
					Drawable image = getResources().getDrawable(R.drawable.yes);
					
					int color_on_selected;
					
					if (!MyDealsFragment.dealsList.contains(newDeal)) { 
					
						color_on_selected = Color.rgb(172, 211, 233);
						MyDealsFragment.dealsList.add(newDeal);
					
					} else {
					
						image = getResources().getDrawable(R.drawable.no);
						color_on_selected = Color.rgb(204, 204, 204);
						MyDealsFragment.dealsList.remove(newDeal);
					}
			
					view.setBackgroundColor(color_on_selected);
					
					for(int i = 0; i < ((ViewGroup)view).getChildCount(); i++) {
					   
						View nextChild = ((ViewGroup)view).getChildAt(i);
					    			    
					    nextChild.setBackgroundColor(color_on_selected);
					}
					
					button_switch.setImageDrawable(image);
				}
			});
		}
	}

	@Override
	public void onDestroyView() {

		if (view != null)
			((ViewGroup) view.getParent()).removeAllViews();

		ListView lv = (ListView) getActivity().findViewById(
				R.id.deals_list_view);

		if (lv != null) {
			((ViewGroup) lv.getParent()).removeView(lv);
			lv.removeAllViews();
		}

		super.onDestroyView();
	}

	public Deal getCurrentFlight() {
		return currentDeal;
	}

	private String getCity(String city) {
		return city.split("\\,")[0];
	}
}
