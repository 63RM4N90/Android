package com.example.handlers;

import java.util.HashMap;
import java.util.Map;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.clickntravel.R;
import com.example.fragments.AboutUsFragment;
import com.example.fragments.AddCommentFragment;
import com.example.fragments.AddFlightFragment;
import com.example.fragments.BaseFragment;
import com.example.fragments.CommentListFragment;
import com.example.fragments.FlightListFragment;
import com.example.fragments.MainFragment;
import com.example.fragments.MyDealsFragment;
import com.example.fragments.MyFlightsFragment;
import com.example.utils.FragmentKey;

public class FragmentHandler {

	Map<FragmentKey, Fragment> fragmentMap = new HashMap<FragmentKey, Fragment>();
	FragmentManager fragmentManager;
	
	public FragmentHandler(FragmentManager fManager) {	
		this.fragmentMap.put(FragmentKey.MAIN, new MainFragment());
		this.fragmentMap.put(FragmentKey.MY_FLIGHTS, new MyFlightsFragment());
		this.fragmentMap.put(FragmentKey.ABOUT_US, new AboutUsFragment());
		this.fragmentMap.put(FragmentKey.BASE, new BaseFragment());
		this.fragmentMap.put(FragmentKey.ADD_FLIGHT, new AddFlightFragment());
		this.fragmentMap.put(FragmentKey.FLIGHT_LIST, new FlightListFragment());
		this.fragmentMap.put(FragmentKey.MY_DEALS, new MyDealsFragment());
		this.fragmentMap.put(FragmentKey.ADD_COMMENT, new AddCommentFragment());
		this.fragmentMap.put(FragmentKey.SEE_COMMENTS, new CommentListFragment());
		
		this.fragmentManager = fManager;
	}
	
	public void setFragment(FragmentKey fragmentKey) {		
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		
		transaction.replace(R.id.container, fragmentMap.get(fragmentKey)).addToBackStack(null).commit();	
	}
	
	public Fragment getFragment(FragmentKey fragmentKey) {
		return fragmentMap.get(fragmentKey);
	}
	
	public void removePreferenceResource() {
		fragmentMap.get(FragmentKey.BASE).onDestroy();
	}
}
