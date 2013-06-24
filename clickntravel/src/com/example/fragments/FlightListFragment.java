package com.example.fragments;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api.ApiIntent;
import com.example.api.ApiResultReceiver;
import com.example.api.Callback;
import com.example.clickntravel.MainActivity;
import com.example.clickntravel.R;
import com.example.utils.AddedFlight;
import com.example.utils.Airline;
import com.example.utils.CustomAdapter;

public class FlightListFragment extends Fragment {

	public static List<AddedFlight> flightList = new ArrayList<AddedFlight>();
	private CustomAdapter adapter;
	public static AddedFlight currentFlight;
	private View view;
	
	private final String fileName = "addedFlightsStorage";
	public FlightListFragment() {
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (view == null) 
			view = inflater.inflate(R.layout.flight_list_fragment, container, false);
		
		
		ListView listView = (ListView) view.findViewById(R.id.flights_list_view);
		
		try {
			retrieveData();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		adapter = new CustomAdapter(getActivity(), flightList);

		/* Assign adapter to ListView */
		listView.setAdapter(adapter); 
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				currentFlight = flightList.get(arg2);
				int screenLayout = getActivity().getResources().getConfiguration().screenLayout;
				if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE){
					((MainActivity)getActivity()).goToNewFavoriteInfoFragmentLarge(currentFlight);
				} else {
					getActivity().getActionBar().selectTab(null);
					((MainActivity)getActivity()).goToNewFavoriteInfoFragment(currentFlight);
				}
			}
		});

		return view;
	}
	
	public void addFlight() {

		String airlineName = getElementString(R.id.airline_input);
		if (MyFlightsFragment.airlinesMap == null){
			Toast.makeText(getActivity(), getActivity().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
			return;
		}
		Airline airline = MyFlightsFragment.airlinesMap.get(airlineName);
		
		if (airline == null) {
			Toast.makeText(getActivity(), getActivity().getString(R.string.invalid_airline), Toast.LENGTH_SHORT).show();
			return;
		}

		
		Callback callback = new Callback() {
			public void handleResponse(JSONObject response) {
				try {
					if (response.has("error")) {
						Toast.makeText(getActivity().getApplicationContext(),
								R.string.get_fligh_error,
								Toast.LENGTH_SHORT).show();
						return;
					}
					JSONObject joStatus = response.getJSONObject("status");
					AddedFlight flight = new AddedFlight(joStatus);
					if (!flightList.contains(flight)) {
						storeOnSharedPreferences(joStatus, flight.getKey());
						flightList.add(flight);
						adapter.notifyDataSetChanged();
					} else {
						Toast.makeText(getActivity(), R.string.already_added, Toast.LENGTH_SHORT).show();
					}
					eraseField(R.id.flight_number_input);
					eraseField(R.id.airline_input);
				} catch (JSONException e) {		}
			}
			
		};

		ApiResultReceiver receiver = new ApiResultReceiver(new Handler(), callback);
		ApiIntent intent = new ApiIntent("GetFlightStatus", "Status", receiver, this.getActivity());
		LinkedList<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("airline_id", airline.getId()));
		params.add(new BasicNameValuePair("flight_num", getElementString(R.id.flight_number_input)));
		intent.setParams(params);
		this.getActivity().startService(intent);
		
	}
	
	private String getElementString(int elementId){
		return ((TextView)getActivity().findViewById(elementId)).getText().toString();
	}

	
	private void storeOnSharedPreferences(JSONObject favorite, String uniqueKey) {
		SharedPreferences prefs = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putString(uniqueKey, favorite.toString()).commit();
	}
	
	@SuppressWarnings("unchecked")
	private void retrieveData() throws JSONException {
		FlightListFragment.flightList = new ArrayList<AddedFlight>();
		SharedPreferences prefs = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Map<String, String> map = (Map<String,String>)prefs.getAll();
		for (String s: map.values()){
			flightList.add(new AddedFlight(new JSONObject(s)));
		}
	}
	
	private void eraseField(int fieldId) {
		TextView tv = (TextView) getActivity().findViewById(fieldId);
		tv.setText("");
	}
	
	@Override
	public void onDestroyView() {
		if (view != null)
			((ViewGroup)view.getParent()).removeAllViews();
		ListView lv = (ListView) getActivity().findViewById(R.id.flights_list_view);
		if (lv != null) {
			((ViewGroup)lv.getParent()).removeView(lv);
			lv.removeAllViews();
		}
		super.onDestroyView();
	}


	public void removeFlight() {
		flightList.remove(currentFlight);
		removeFromSavedFlights(fileName, currentFlight.getKey());
		adapter.notifyDataSetChanged();
		ListView lv = (ListView) view.findViewById(R.id.flights_list_view);
		lv.invalidateViews();
		
	}
	
	public void removeFromSavedFlights(String myFileName, String key){
		SharedPreferences prefs = getActivity().getSharedPreferences(myFileName, Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.remove(key).commit();
	}

}
