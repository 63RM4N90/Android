package com.example.fragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clickntravel.R;

public class MyDealsFragment extends Fragment {

	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ActionBar actionBar = getActivity().getActionBar();
		
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		actionBar.setCustomView(R.layout.mydeals_abs_layout);
		
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(R.string.main_button_mydeals);
		actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        return inflater.inflate(R.layout.my_deals_fragment, container, false);
    }
	
}