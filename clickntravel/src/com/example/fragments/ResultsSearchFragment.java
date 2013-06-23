package com.example.fragments;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.api.ApiIntent;
import com.example.api.ApiResultReceiver;
import com.example.api.Callback;
import com.example.clickntravel.R;
import com.example.utils.DateUtil;
import com.example.utils.Deal;

public class ResultsSearchFragment extends Fragment {

	// private Map<String, Deal> dealsMap;

	private List<String> dealPrices = new ArrayList<String>();
	private List<Deal> dealsList = new ArrayList<Deal>();

	// Esto está hardcodeado a que from sea Buenos Aires TODO
	private String nameFrom;
	private String idFrom = "BUE";
	private String nameTo;
	private String idTo;

	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ActionBar actionBar = getActivity().getActionBar();

		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.resultssearch_abs_layout);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		if (view == null)
			view = inflater.inflate(R.layout.deal_list_fragment, container,
					false);

		Bundle arguments = getArguments();

		idTo = arguments.getString("cityId");
		nameTo = arguments.getString("cityName");

		ListView listView = (ListView) view.findViewById(R.id.deals_list_view);

		createDeals();

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

				if (id.equals(idTo)) {

					dealPrices.add(price);
				}
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

									addDeal(indexMinPrice, minPrice, dealArray);

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

							nameFrom = segments.getJSONObject("departure")
									.optString("cityName");

							Deal curr = new Deal(idFrom, nameFrom, idTo,
									nameTo, price, airlineId, flightId,
									flightNumber);

							dealsList.add(curr);

							// Log.d("deal", curr.toString());

						} catch (JSONException e) {
						}
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
					// TODO Auto-generated catch block
					e.printStackTrace();
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

	@Override
	public void onDestroyView() {

		if (view != null) {

			((ViewGroup) view.getParent()).removeAllViews();
		}

		ListView lv = (ListView) getActivity().findViewById(
				R.id.deals_list_view);

		if (lv != null) {

			((ViewGroup) lv.getParent()).removeView(lv);
			lv.removeAllViews();
		}

		super.onDestroyView();
	}
}
