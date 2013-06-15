package com.example.clickntravel;


import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.fragments.ConfigurationFragment;
import com.example.handlers.FragmentHandler;
import com.example.utils.FragmentKey;

public class MainActivity extends FragmentActivity {

	FragmentHandler fragmentHandler;
	
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
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            Intent intent = new Intent(this, MainActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
			case R.id.menu_settings:
				getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, new ConfigurationFragment())
				.addToBackStack(null)
				.commit();
			break;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	    return true;
	}
	
	public void onClickMyFlights(View view) {
		this.fragmentHandler.setFragment(FragmentKey.MY_FLIGHTS);
	}
	
	public void onClickAboutUs(View view) {
		this.fragmentHandler.setFragment(FragmentKey.ABOUT_US);
	}
	
	public void onClickConfiguration(View view) {
		this.fragmentHandler.setFragment(FragmentKey.CONFIGURATION);
	}
}
