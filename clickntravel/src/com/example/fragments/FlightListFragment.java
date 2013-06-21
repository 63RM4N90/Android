package com.example.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clickntravel.MainActivity;
import com.example.clickntravel.R;
import com.example.handlers.ActionHandler;
import com.example.utils.AddedFlight;
import com.example.utils.Airline;
import com.example.utils.MyFlightsCases;

public class FlightListFragment extends Fragment implements ActionHandler {

	public static List<AddedFlight> flightList = new ArrayList<AddedFlight>();
	private SimpleAdapter adapter;
	private List<Map<String, String>> adapterDataSet = new ArrayList<Map<String,String>>();
	private Map<String, Airline> airlinesMap;
	private AddedFlight currentFlight;
	private View view;
	
	private final String firstRowName = "flightInfo";
	private final String secondRowName = "citiesInfo";
	
	private final String fileName = "favoritesStorage";
	private final String preferencesFileName = "favoritesPreferencesStorage";
	
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
		
		generateAdapterDataSet();
		
		String[] columns = {firstRowName, secondRowName};
		int[] renderTo = {android.R.id.text1, android.R.id.text2};
		adapter = new SimpleAdapter(getActivity(), adapterDataSet, android.R.layout.two_line_list_item, columns, renderTo);

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
	
	public void addFavorite(View view) {
		Map<String, String> map = new HashMap<String, String>();

		String airlineName = getElementString(R.id.airline_input);
		if (airlinesMap == null){
			Toast.makeText(getActivity(), getActivity().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
			return;
		}
		Airline airline = airlinesMap.get(airlineName);
		
		if (airline == null) {
			Toast.makeText(getActivity(), getActivity().getString(R.string.invalid_airline), Toast.LENGTH_SHORT).show();
			return;
		}
		
		map.put("airline_id", airline.getId());
		map.put("flight_num", getElementString(R.id.flight_number_input));

//		QueryIntent query = new QueryIntent(new RequestReceiver() {
//
//			@Override
//			public void onStarted() {
//			}
//
//			@Override
//			public void onData(String data) {
//				JSONObject jo;
//				try {
//					jo = new JSONObject(data);
//					if (jo.has("error")) {
//						Toast.makeText(getActivity().getApplicationContext(),
//								jo.getJSONObject("error").getString("message"),
//								Toast.LENGTH_SHORT).show();
//						return;
//					}
//					JSONObject joStatus = jo.getJSONObject("status");
//					Favorite flight = new Favorite(joStatus);
//					if (!favList.contains(flight)) {
//						storeOnSharedPreferences(joStatus, flight.getKey());
//						favList.add(flight);
//						addToAdapterDataSet(flight);
//						adapter.notifyDataSetChanged();
//					} else {
//						Toast.makeText(getActivity(), getActivity().getString(R.string.flight_already_on_favorites), Toast.LENGTH_SHORT).show();
//					}
//					eraseField(R.id.flight_number_input);
//					eraseField(R.id.airline_input);
//					
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//
//			}
//
//			@Override
//			public void onError(String error) {
//			}
//
//		}, FlightsAPIService.GET, "Status", "GetFlightStatus", map, "", getActivity());
//		getActivity().startService(query);
	}
	
	private String getElementString(int elementId){
		return ((TextView)getActivity().findViewById(elementId)).getText().toString();
	}
	
	private void generateAdapterDataSet() {
		adapterDataSet.removeAll(adapterDataSet);
		for(AddedFlight f : flightList)
			addToAdapterDataSet(f);
	}
	
	private void addToAdapterDataSet(AddedFlight f) {
		Map<String,String> item = new HashMap<String,String>();
		String departure = f.getDeparture().getCityName();
		String arrival = f.getArrival().getCityName();
		item.put(firstRowName, getActivity().getString(R.string.flight) + " " + f.getFlightNumber() + " - " + f.getAirline().getName());
		item.put(secondRowName, getCityFromString(departure) + " - " + getCityFromString(arrival));
		adapterDataSet.add(item);
	}
	
	private String getCityFromString(String s) {
		return s.substring(0, s.indexOf(','));
	}


	public void Handle(MyFlightsCases c, View view) {
		switch (c) {
		case ADD_FLIGHT:
			addFavorite(view);
			break;
		case REMOVE_FLIGHT:
			removeFavorite();
			break;
		}
		return;	
	}
	
	private void removeFavorite() {
		flightList.remove(currentFlight);
		generateAdapterDataSet();
		removeFromMyFlights(fileName, currentFlight.getKey());
		removeFromMyFlights(preferencesFileName, currentFlight.getKey());
		adapter.notifyDataSetChanged();
		ListView lv = (ListView) view.findViewById(R.id.flights_list_view);
		lv.invalidateViews();
	}
	
	private void storeOnSharedPreferences(JSONObject favorite, String uniqueKey) {
		SharedPreferences prefs = getActivity().getSharedPreferences(fileName, Context.MODE_WORLD_WRITEABLE);
		Editor editor = prefs.edit();
		editor.putString(uniqueKey, favorite.toString()).commit();
	}
	
	private void retrieveData() throws JSONException {
		FlightListFragment.flightList = new ArrayList<AddedFlight>();
		SharedPreferences prefs = getActivity().getSharedPreferences(fileName, Context.MODE_WORLD_READABLE);
		Map<String, String> map = (Map<String,String>)prefs.getAll();
		for (String s: map.values()){
			flightList.add(new AddedFlight(new JSONObject(s)));
		}
		prefs = getActivity().getSharedPreferences(preferencesFileName, Context.MODE_WORLD_READABLE);
		map = (Map<String,String>)prefs.getAll();
		AddedFlight f;
		JSONObject jsonPrefs;
		for (String s: map.keySet()){
			f = getMyFlights(s);
			jsonPrefs = new JSONObject(map.get(s));
			if (f != null) {
//				f.setNotificationConfiguration(jsonPrefs);
			}
		}
	}
	
	private void eraseField(int fieldId) {
		TextView tv = (TextView) getActivity().findViewById(fieldId);
		tv.setText("");
	}
	
	private AddedFlight getMyFlights(String key) {
		for(AddedFlight f : flightList) {
			if (f.getKey().equals(key))
				return f;
		}
		return null;
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

	public void setAirlinesMap(Map<String,Airline> map){
		airlinesMap = map;
	}


	public AddedFlight getCurrentFlight() {
		return currentFlight;
	}
	
	public void removeFromMyFlights(String myFileName, String key){
		SharedPreferences prefs = getActivity().getSharedPreferences(myFileName, Context.MODE_WORLD_WRITEABLE);
		Editor editor = prefs.edit();
		editor.remove(key).commit();
	}

}
