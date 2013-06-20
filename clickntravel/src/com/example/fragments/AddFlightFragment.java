package com.example.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.clickntravel.R;
import com.example.utils.Airline;
import com.example.utils.FlightsAPIService;
import com.example.utils.QueryIntent;
import com.example.utils.RequestReceiver;

public class AddFlightFragment extends Fragment {

	private View view;
	private Map<String, Airline> airlinesMap;

	public AddFlightFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.add_flight_fragment, container, false);
			

			QueryIntent query = new QueryIntent(new RequestReceiver() {

				@Override
				public void onStarted() {
				}

				@Override
				public void onData(String data) {
					if (getActivity().findViewById(R.id.airline_input) == null){
						return;
					}	
					Airline airline;
					airlinesMap = new HashMap<String,Airline>();
					List<String> lstAirlines = new ArrayList<String>();
					JSONObject jo;
					try {
						jo = new JSONObject(data);
						JSONArray joAirlines = jo.getJSONArray("airlines");
						for (int i = 0; i < joAirlines.length(); i++) {
							airline = new Airline(joAirlines.getJSONObject(i));
							lstAirlines.add(airline.getName());
							airlinesMap.put(airline.getName(), airline);
						}

						ArrayAdapter<String> adapter = new ArrayAdapter<String>(
								getActivity(),
								android.R.layout.simple_dropdown_item_1line,
								lstAirlines);

						AutoCompleteTextView textView = (AutoCompleteTextView) getActivity()
								.findViewById(R.id.airline_input);
						
						Log.d("Gabo PUTO!", airlinesMap.toString());
						
						textView.setAdapter(adapter);

					} catch (JSONException e) {
						Log.d("Gabo PUTO!","caca");
						e.printStackTrace();
					}

				}

				@Override
				public void onError(String error) {
				}

			}, FlightsAPIService.GET, "Misc", "GetAirlines", new HashMap<String,String>(), "", getActivity());
			
			getActivity().startService(query);
		}

		return view;
	}

	@Override
	public void onDestroyView() {
		((ViewGroup) view.getParent()).removeAllViews();
		super.onDestroyView();
	}
	
	public Map<String, Airline> getAirlinesMap(){
		return airlinesMap;
	}

}
