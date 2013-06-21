package com.example.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.api.ApiIntent;
import com.example.api.ApiResultReceiver;
import com.example.api.Callback;
import com.example.clickntravel.R;
import com.example.utils.Airline;

public class AddFlightFragment extends Fragment {

	private View view;
	private Map<String, Airline> airlinesMap;
	List<String> lstAirlines = new  ArrayList<String>();


	public AddFlightFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.add_flight_fragment, container, false);
			airlinesMap = new HashMap<String, Airline>();
			Callback callback = new Callback() {
				public void handleResponse(JSONObject response) {
					try {
						JSONArray cityArray = response.getJSONArray("airlines");
						List<String> airlinesList = new LinkedList<String>();
						for (int i = 0 ; i < cityArray.length() ; i++) {
							String name = cityArray.getJSONObject(i).optString("name");
							String id = cityArray.getJSONObject(i).optString("airlineId");
							Log.d("airline", name);
							airlinesMap.put(name, new Airline(id, name, "que_se_yo"));
						}
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(
								getActivity(),
								android.R.layout.simple_dropdown_item_1line,
								new ArrayList<String>(airlinesMap.keySet()));

						AutoCompleteTextView textView = (AutoCompleteTextView) getActivity().findViewById(R.id.airline_input);
						
						textView.setAdapter(adapter);
					} catch (JSONException e) {		}
				}
				
			};

			ApiResultReceiver receiver = new ApiResultReceiver(new Handler(), callback);
			ApiIntent intent = new ApiIntent("GetAirlines", "Misc", receiver, this.getActivity());
			intent.setParams(new LinkedList<NameValuePair>());
			this.getActivity().startService(intent);
		}

		return view;
	}

	@Override
	public void onDestroyView() {
		((ViewGroup) view.getParent()).removeAllViews();
		super.onDestroyView();
	}
	
	public Map<String, Airline> getAirlines(){
		return airlinesMap;
	}

}