package com.example.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.clickntravel.R;

public class ConfigurationFragment extends PreferenceFragment {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		
        addPreferencesFromResource(R.xml.settings);
    }
    
}