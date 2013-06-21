package com.example.handlers;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.clickntravel.R;
import com.example.fragments.AboutUsFragment;
import com.example.fragments.BaseFragment;
import com.example.fragments.MainFragment;
import com.example.fragments.MyDealsFragment;
import com.example.fragments.MyFlightsFragment;
import com.example.fragments.ResultsSearchFragment;
import com.example.utils.FragmentKey;

public class FragmentHandler {

	Map<FragmentKey, Fragment> fragmentMap = new HashMap<FragmentKey, Fragment>();
	FragmentManager fragmentManager;
	
	public FragmentHandler(FragmentManager fManager) {	
		this.fragmentMap.put(FragmentKey.MAIN, new MainFragment());
		this.fragmentMap.put(FragmentKey.MY_FLIGHTS, new MyFlightsFragment());
		this.fragmentMap.put(FragmentKey.ABOUT_US, new AboutUsFragment());
		this.fragmentMap.put(FragmentKey.BASE, new BaseFragment());
		this.fragmentMap.put(FragmentKey.MY_DEALS, new MyDealsFragment());
		this.fragmentMap.put(FragmentKey.SEARCH_DEALS_LIST, new ResultsSearchFragment());
		
		this.fragmentManager = fManager;
	}
	
	public void setFragment(FragmentKey fragmentKey) {		
		setFragment(fragmentMap.get(fragmentKey));
	}
	
	public void setFragment(FragmentKey fragmentKey, Bundle bundle) {
		Fragment fragment = fragmentMap.get(fragmentKey);
		fragment.setArguments(bundle);
		setFragment(fragment);
	}
	
	private void setFragment(Fragment fragment) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		
		transaction.replace(R.id.container, fragment).addToBackStack(null).commit();
	}
	
	public void removePreferenceResource() {
		fragmentMap.get(FragmentKey.BASE).onDestroy();
	}
}
