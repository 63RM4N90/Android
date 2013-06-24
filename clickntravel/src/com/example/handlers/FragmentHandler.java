package com.example.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.os.Bundle;
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
import com.example.fragments.ResultsSearchFragment;
import com.example.utils.FragmentKey;

public class FragmentHandler {

	Map<FragmentKey, Fragment> fragmentMap = new HashMap<FragmentKey, Fragment>();
	FragmentManager fragmentManager;
	Fragment currentFragment;

	public FragmentHandler(FragmentManager fManager) {
		this.fragmentMap.put(FragmentKey.MAIN, new MainFragment());
		this.fragmentMap.put(FragmentKey.SEARCH_DEALS_LIST, new ResultsSearchFragment());
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

	public Fragment getFragment(FragmentKey fragmentKey) {
		return fragmentMap.get(fragmentKey);
	}

	public FragmentKey getCurrentKey() {
		Set<FragmentKey> keys = fragmentMap.keySet();
		for (FragmentKey each : keys)
			if (fragmentMap.get(each).equals(currentFragment))
				return each;
		
		return FragmentKey.NONE;
	}
}
