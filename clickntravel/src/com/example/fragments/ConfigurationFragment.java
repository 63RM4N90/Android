package com.example.fragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.clickntravel.R;

public class ConfigurationFragment extends PreferenceFragment {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		ActionBar actionBar = getActivity().getActionBar();
		
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		actionBar.setCustomView(R.layout.configuration_abs_layout);	
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(R.string.main_button_configuration);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true); 
        addPreferencesFromResource(R.xml.settings);
    }
    
}