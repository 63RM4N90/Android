package com.example.fragments;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.clickntravel.MainActivity;
import com.example.clickntravel.R;
import com.example.utils.FragmentKey;

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
    
//	mPrefsFragment.getPreferenceScreen().removeAll();
}