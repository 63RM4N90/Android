package com.example.fragments;

import java.util.Map;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.clickntravel.MainActivity;
import com.example.clickntravel.R;
import com.example.handlers.ActionHandler;
import com.example.utils.Airline;
import com.example.utils.FragmentKey;
import com.example.utils.MyFlightsCases;


public class MyFlightsFragment extends Fragment{

	View view;
	ViewGroup vg;
	Fragment addFragment;
	Fragment listFragment;
	
	public MyFlightsFragment(){
		/*empty constructor*/
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			ActionBar actionBar = getActivity().getActionBar();
			FragmentTransaction ft;
			
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
			actionBar.setCustomView(R.layout.myflights_abs_layout);
			actionBar.setDisplayShowTitleEnabled(true);
			actionBar.setTitle(R.string.main_button_myflights);
			actionBar.setDisplayShowHomeEnabled(true);
	        actionBar.setHomeButtonEnabled(true);
	        
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
	public void onDestroyView() {
		((ViewGroup)view.getParent()).removeAllViews();
		super.onDestroyView();
	}

	
	
	public void addFlight(View view) {
		((FlightListFragment)listFragment).setAirlines(((AddFlightFragment) addFragment).getAirlines());
		((FlightListFragment) listFragment).addFlight();
	}
	
}

