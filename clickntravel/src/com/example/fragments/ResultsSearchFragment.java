package com.example.fragments;

import java.util.ArrayList;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.api.ApiIntent;
import com.example.api.ApiResultReceiver;
import com.example.api.Callback;
import com.example.clickntravel.R;
import com.example.utils.Deal;

public class ResultsSearchFragment extends Fragment {

	private Map<String, Deal> dealsMap;

	// Esto está hardcodeado a que from sea Buenos Aires TODO
	private String nameFrom;
	private String idFrom = "BUE";
	private String nameTo;
	private String idTo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ActionBar actionBar = getActivity().getActionBar();

		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.resultssearch_abs_layout);

		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(R.string.main_button_resultssearch);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		return inflater.inflate(R.layout.results_search_fragment, container, false);
	}

	public void createDeals() {

		dealsMap = new HashMap<String, Deal>();

		Callback callback = new Callback() {

			public void handleResponse(JSONObject response) {

				try {

					JSONArray dealArray = response.getJSONArray("deals");

					for (int i = 0; i < dealArray.length(); i++) {

						String price = dealArray.getJSONObject(i).optString("price");
						String id = dealArray.getJSONObject(i).optString("cityId");

						addDeal(id, price);
					}

				} catch (JSONException e) {
				}
			}

			private void addDeal(String id, String price) {

				if (!id.equals(idTo)) {
					
					return;
				}
				
				dealsMap.put(nameTo, new Deal(idFrom, nameFrom, idTo, nameTo, price));
			}

		};

		ApiResultReceiver receiver = new ApiResultReceiver(new Handler(),
				callback);
		ApiIntent intent = new ApiIntent("GetFlightDeals", "Booking", receiver,
				this.getActivity());

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

		nameValuePair.add(new BasicNameValuePair("from", idFrom));

		intent.setParams(nameValuePair);

		this.getActivity().startService(intent);
	}
}
