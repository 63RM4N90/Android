package com.example.fragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clickntravel.R;

public class MyFlightsFragment extends Fragment {

	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ActionBar actionBar = getActivity().getActionBar();
		
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		actionBar.setCustomView(R.layout.myflights_abs_layout);
		
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(R.string.main_button_myflights);
		actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
		return inflater.inflate(R.layout.my_flights_fragment, container, false);
    }
	
}

