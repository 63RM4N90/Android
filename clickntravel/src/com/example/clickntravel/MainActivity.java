package com.example.clickntravel;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.fragments.ConfigurationFragment;
import com.example.fragments.FlightInfoFragment;
import com.example.fragments.MyFlightsFragment;
import com.example.handlers.FragmentHandler;
import com.example.utils.AddedFlight;
import com.example.utils.FragmentKey;

public class MainActivity extends FragmentActivity {

	private static final int GROUP_ID = 1;
	private static final int CONFIG_ID = 1;
    private ConfigurationFragment mPrefsFragment = null;
	private FragmentHandler fragmentHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ColorDrawable actionBarBackground = new ColorDrawable(Color.rgb(12, 129, 199));		
		ActionBar actionBar = getActionBar();
		
        setContentView(R.layout.activity_main);
        this.fragmentHandler = new FragmentHandler(getSupportFragmentManager());
        this.fragmentHandler.setFragment(FragmentKey.MAIN);  
        actionBar.setBackgroundDrawable(actionBarBackground);
        actionBar.setIcon(R.drawable.back);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) { 
		menu.add(GROUP_ID, CONFIG_ID, CONFIG_ID, R.string.main_button_configuration);
		return super.onCreateOptionsMenu(menu); 
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Intent intent = new Intent(this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				return true;
			case CONFIG_ID:
				mPrefsFragment  = new ConfigurationFragment();
                FragmentManager mFragmentManager = getFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager
                                        .beginTransaction();
                mFragmentTransaction.replace(android.R.id.content, mPrefsFragment);
                mFragmentTransaction.addToBackStack(null).commit();
				this.fragmentHandler.setFragment(FragmentKey.BASE);
				return true;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onClickMyFlights(View view) {
		this.fragmentHandler.setFragment(FragmentKey.MY_FLIGHTS);
	}
	
	public void onClickAboutUs(View view) {
		this.fragmentHandler.setFragment(FragmentKey.ABOUT_US);
	}
	
	public void onClickMyDeals(View view) {
		this.fragmentHandler.setFragment(FragmentKey.MY_DEALS);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK && mPrefsFragment != null) {
//	    	mPrefsFragment.getPreferenceScreen().removeAll();
//	    	mPrefsFragment = null;
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	public void goToNewFavoriteInfoFragment(AddedFlight f){
		getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, new FlightInfoFragment(f))
					.addToBackStack(null)
					.commit();
	}
	
	public void goToNewFavoriteInfoFragmentLarge(AddedFlight f){
		//checkear que esto este bien, el container
		getSupportFragmentManager().beginTransaction().replace(R.id.container, new FlightInfoFragment(f)).addToBackStack(null).commit();
	}
	
	public void addFlight(View view) {
		((MyFlightsFragment) fragmentHandler.getFragment(FragmentKey.MY_FLIGHTS)).addFlight(view);
	}
	
	public FragmentHandler getFragmentHandler(){
		return fragmentHandler;
	}

}
