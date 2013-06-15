package com.example.clickntravel;



import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.fragments.ConfigurationFragment;
import com.example.handlers.FragmentHandler;
import com.example.utils.FragmentKey;

public class MainActivity extends FragmentActivity {

	private static final int GROUP_ID = 1;
	private static final int CONFIG_ID = 1;
	
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
		Intent intent = new Intent(this, MainActivity.class);
		
		switch (item.getItemId()) {
			case android.R.id.home:
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				return true;
			case CONFIG_ID:
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.container, new ConfigurationFragment()).commit();
			
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

}
