package com.example.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clickntravel.R;
import com.example.handlers.ImageHandler;
import com.example.handlers.StatusHandler;
import com.example.utils.AddedFlight;

public class FlightInfoFragment extends Fragment {

	private AddedFlight currentFlight;
	private View view;
	private final String myFlightsFileName = "MyFlightsStorage";

	public FlightInfoFragment() {

	}

	public FlightInfoFragment(AddedFlight f) {
		currentFlight = f;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.flight_details_fragment, container, false);
		ImageView iv = (ImageView)view.findViewById(R.id.airline_name_value);
		ImageHandler ih = new ImageHandler();
		StatusHandler sh = new StatusHandler();
		
		iv.setImageResource(ih.getImage(currentFlight.getAirline().getName()));
		setIntoTextView(R.id.flight_number_value, " " + currentFlight.getFlightNumber());
		setIntoTextView(R.id.departure_city_value, " " +  getCityFromString(currentFlight.getDeparture().getCityName()));
		setIntoTextView(R.id.arrival_city_value, " " + getCityFromString(currentFlight.getArrival().getCityName()));
		TextView textView = (TextView) view.findViewById(R.id.flight_status_value);
		textView.setTextColor(sh.getStatusColor(currentFlight.getFlightStatus()));
		textView.setBackgroundColor(sh.getStatusBackgroundColor(currentFlight.getFlightStatus()));
		setIntoTextView(R.id.flight_status_value, getActivity().getString(sh.getStatus(currentFlight.getFlightStatus())));
		setIntoTextView(R.id.departure_airport_terminal_value, " " + currentFlight.getDeparture().getAirportTerminal(getActivity()));
		setIntoTextView(R.id.arrival_airport_terminal_value, " " + currentFlight.getArrival().getAirportTerminal(getActivity()));
		setIntoTextView(R.id.departure_airport_gate_value, " " + currentFlight.getDeparture().getAirportGate(getActivity()));
		setIntoTextView(R.id.departure_scheduled_time_value, " " + currentFlight.getDeparture().getScheduledTime());
		setIntoTextView(R.id.arrival_airport_gate_value, " " + currentFlight.getArrival().getAirportGate(getActivity()));
		setIntoTextView(R.id.arrival_scheduled_time_value, " " + currentFlight.getArrival().getScheduledTime());
		return view;
	}

	private void setIntoTextView(int textViewId, String string) {
		TextView textView = (TextView) view.findViewById(textViewId);
		textView.setText(string);
	}

	private String getCityFromString(String s) {
		return s.substring(0, s.indexOf(','));
	}

	@Override
	public void onDestroyView() {
		SharedPreferences prefs = getActivity().getSharedPreferences(myFlightsFileName, Context.MODE_WORLD_WRITEABLE);
		Editor editor = prefs.edit();
		String uniqueKey = currentFlight.getKey();
		// NotificationConfiguration conf = currentFlight.getConfig();
		// conf.setNotifyOnStatusChanged(getChecked(R.id.status_check));
		// conf.setNotifyOnGateChanged(getChecked(R.id.gate_check));
		// conf.setNotifyOnTerminalChanged(getChecked(R.id.terminal_check));
		// // conf.setNotifyOnScheduledTimeChanged(getChecked(R.id.time_check));
		//
		// JSONObject jsonPrefs;
		//
		// try {
		// jsonPrefs = currentFlight.generateJsonPreferences();
		// editor.putString(uniqueKey, jsonPrefs.toString()).commit();
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		super.onDestroyView();
	}
}
