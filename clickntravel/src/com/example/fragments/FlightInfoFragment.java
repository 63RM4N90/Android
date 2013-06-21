package com.example.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clickntravel.R;
import com.example.handlers.ImageHandler;
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
		
		iv.setImageResource(ih.getImage(currentFlight.getAirline().getName()));
		setIntoTextView(R.id.flight_number, getActivity().getString(R.string.flight_number) + " " + currentFlight.getFlightNumber());
		setIntoTextView(R.id.departure_city_value, getCityFromString(currentFlight.getDeparture().getCityName()));
		setIntoTextView(R.id.arrival_city_value, getCityFromString(currentFlight.getArrival().getCityName()));
		setIntoTextView(R.id.flight_status, getStatus(currentFlight.getFlightStatus()));
		setIntoTextView(R.id.departure_airport_terminal, currentFlight.getDeparture().getAirportTerminal());
		setIntoTextView(R.id.arrival_airport_terminal, currentFlight.getArrival().getAirportTerminal());
		setIntoTextView(R.id.departure_airport_gate, currentFlight.getDeparture().getAirportGate());
		setIntoTextView(R.id.departure_scheduled_time, currentFlight.getDeparture().getScheduledTime());
		setIntoTextView(R.id.arrival_airport_gate, currentFlight.getArrival().getAirportGate());
		setIntoTextView(R.id.arrival_scheduled_time, currentFlight.getArrival().getScheduledTime());
		return view;
	}

	private void setIntoTextView(int textViewId, String string) {
		TextView textView = (TextView) view.findViewById(textViewId);
		textView.setText(string);
	}

	private String getStatus(String status) {
		Log.d("Flight status", status);
		if (status.equals("S")) {
			return getActivity().getString(R.string.scheduled);
		} else if (status.equals("A")) {
			return getActivity().getString(R.string.active);
		} else if (status.equals("D")) {
			return getActivity().getString(R.string.deviated);
		} else if (status.equals("L")) {
			return getActivity().getString(R.string.landed);
		} else if (status.equals("C")) {
			return getActivity().getString(R.string.cancelled);
		}
		return getActivity().getString(R.string.unknown);

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
