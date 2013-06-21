package com.example.fragments;

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
import com.example.utils.AddedFlight;

public class FlightInfoFragment extends Fragment {

	private AddedFlight currentFlight;
	private View view;

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
		setIntoTextView(R.id.flight_status, currentFlight.getFlightStatus());
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

	private String getCityFromString(String s) {
		return s.substring(0, s.indexOf(','));
	}
}
