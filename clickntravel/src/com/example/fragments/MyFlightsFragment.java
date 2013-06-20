package com.example.fragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clickntravel.R;
import com.example.utils.ActionHandler;
import com.example.utils.MyFlightsCases;

//return inflater.inflate(R.layout.my_flights_fragment, container, false);

public class MyFlightsFragment extends Fragment implements ActionHandler{

	View view;
	ViewGroup vg;
	Fragment addFragment;
	Fragment listFragment;
	
	public MyFlightsFragment(){
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			ActionBar actionBar = getActivity().getActionBar();
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
			actionBar.setCustomView(R.layout.myflights_abs_layout);
			
			actionBar.setDisplayShowTitleEnabled(true);
			actionBar.setTitle(R.string.main_button_myflights);
			actionBar.setDisplayShowHomeEnabled(true);
	        actionBar.setHomeButtonEnabled(true);
	        
			vg = container;
			view = inflater.inflate(R.layout.my_flights_fragment, container, false);
			FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
			addFragment = new AddFlightFragment();
			listFragment = new FlightListFragment();
			
			ft.add(R.id.favorites_add_fragment_container, addFragment);
			ft.add(R.id.favorites_list_fragment_container, listFragment);
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


	public void Handle(MyFlightsCases c, View view) {
		switch (c) {
		case ADD_FAVORITE:
			((FlightListFragment)listFragment).setAirlinesMap(((AddFlightFragment)addFragment).getAirlinesMap());
			((ActionHandler)listFragment).Handle(MyFlightsCases.ADD_FAVORITE, view);
			break;
		case REMOVE_FAVORITE:
			((ActionHandler)listFragment).Handle(MyFlightsCases.REMOVE_FAVORITE, view);
			break;
		}
		return;		
	} 
	
	
	
	
}

