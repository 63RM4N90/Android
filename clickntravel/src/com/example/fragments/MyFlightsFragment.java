package com.example.fragments;

import java.util.Map;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.clickntravel.MainActivity;
import com.example.clickntravel.R;
import com.example.utils.Airline;
import com.example.utils.FragmentKey;


public class MyFlightsFragment extends Fragment{

	View view;
	ViewGroup vg;
	Fragment addFragment;
	Fragment listFragment;
	public static Map<String, Airline> airlinesMap;
	
	public MyFlightsFragment(){
		/*empty constructor*/
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			FragmentTransaction ft;
	        
			vg = container;
			view = inflater.inflate(R.layout.my_flights_fragment, container, false);
			ft = getActivity().getSupportFragmentManager().beginTransaction();
			
			addFragment = ((MainActivity)getActivity()).getFragmentHandler().getFragment(FragmentKey.ADD_FLIGHT);
			listFragment = ((MainActivity)getActivity()).getFragmentHandler().getFragment(FragmentKey.FLIGHT_LIST);
			
			ft.add(R.id.add_flight_container, addFragment);
			ft.add(R.id.flight_list_container, listFragment);
			ft.commit();
		}
		ViewGroup parent = (ViewGroup)vg.getParent();
		parent.removeView(view);
		container.removeView(view);
		return view;
	}

	@Override
	public void onStart() {
		ActionBar actionBar = getActivity().getActionBar();
		
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		actionBar.setCustomView(R.layout.myflights_abs_layout);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        super.onStart();
	}
	
	@Override
	public void onDestroyView() {
		((ViewGroup)view.getParent()).removeAllViews();
		super.onDestroyView();
	}
	
	public void addFlight(View view) {
		((FlightListFragment) listFragment).addFlight();
	}
	
}

