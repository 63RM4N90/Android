package com.example.clickntravel;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.alerts.Alert;
import com.example.alerts.AlertNotification;
import com.example.fragments.AddCommentFragment;
import com.example.fragments.ConfigurationFragment;
import com.example.fragments.FlightInfoFragment;
import com.example.fragments.FlightListFragment;
import com.example.fragments.MyFlightsFragment;
import com.example.handlers.FragmentHandler;
import com.example.notifications.NotificationIntent;
import com.example.utils.AddedFlight;
import com.example.utils.FragmentKey;

public class MainActivity extends FragmentActivity {

	private ConfigurationFragment mPrefsFragment = null;
	private FragmentHandler fragmentHandler;

	MenuItem remove;
	MenuItem submitComment;
	MenuItem comment;
	MenuItem seeComments;
	MenuItem configuration;

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Alert.CONTEXT = this;
		Alert.refreshAlerts();
		PreferenceManager
				.getDefaultSharedPreferences(this)
				.registerOnSharedPreferenceChangeListener(
						new SharedPreferences.OnSharedPreferenceChangeListener() {
							public void onSharedPreferenceChanged(
									SharedPreferences prefs, String key) {
								Alert.refreshAlerts();
							}
						});
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		ColorDrawable actionBarBackground = new ColorDrawable(Color.rgb(12,
				129, 199));
		ActionBar actionBar = getActionBar();
		AlertNotification.context = this;
		NotificationIntent intent = new NotificationIntent(this);
		startService(intent);

		setContentView(R.layout.activity_main);
		this.fragmentHandler = new FragmentHandler(getSupportFragmentManager());
		this.fragmentHandler.setFragment(FragmentKey.MAIN);
		actionBar.setBackgroundDrawable(actionBarBackground);
		actionBar.setIcon(R.drawable.back);
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

	public void goToNewFavoriteInfoFragment(AddedFlight f) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, new FlightInfoFragment(f))
				.addToBackStack(null).commit();
	}

	public void goToNewFavoriteInfoFragmentLarge(AddedFlight f) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, new FlightInfoFragment(f))
				.addToBackStack(null).commit();
	}

	@Override
	public void onBackPressed() {
		hideDetailOptions();
		hideSubmitComment();
		if (fragmentHandler.getCurrentKey().equals(FragmentKey.BASE)) {
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return;
		}
		super.onBackPressed();
	}

	public void addFlight(View view) {
		((MyFlightsFragment) fragmentHandler
				.getFragment(FragmentKey.MY_FLIGHTS)).addFlight(view);
	}

	public FragmentHandler getFragmentHandler() {
		return fragmentHandler;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		createMenu(menu);
		return true;
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return menuSelection(item);
	};

	private boolean menuSelection(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			((FlightListFragment) fragmentHandler.getFragment(FragmentKey.FLIGHT_LIST)).removeFlight();
			this.onBackPressed();
			hideDetailOptions();
			Toast.makeText(this, R.string.remove_flight_toast,
					Toast.LENGTH_SHORT).show();
			return true;
		case 1:
			hideDetailOptions();
			fragmentHandler.setFragment(FragmentKey.ADD_COMMENT);
			return true;
		case 2:
			hideDetailOptions();
			fragmentHandler.setFragment(FragmentKey.SEE_COMMENTS);
			return true;
		case 3:
			mPrefsFragment = new ConfigurationFragment();
			FragmentManager mFragmentManager = getFragmentManager();
			FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
			mFragmentTransaction.replace(android.R.id.content, mPrefsFragment);
			mFragmentTransaction.addToBackStack(null).commit();
			fragmentHandler.setFragment(FragmentKey.BASE);
			return true;
		case 4:
			((AddCommentFragment) fragmentHandler.getFragment(FragmentKey.ADD_COMMENT)).addComment();
			this.onBackPressed();
			return true;
		case android.R.id.home:
			this.onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void createMenu(Menu menu) {
		remove = menu.add(0, 0, 0, "remove");
		comment = menu.add(0, 1, 1, "comment");
		seeComments = menu.add(0, 2, 2, "see comments");
		configuration = menu.add(0, 3, 3, R.string.main_button_configuration);
		submitComment = menu.add(0, 4, 4, "submit comment");

		remove.setIcon(R.drawable.remove);
		comment.setIcon(R.drawable.add_comment);
		seeComments.setIcon(R.drawable.comments);
		submitComment.setIcon(R.drawable.submit_comment);

		remove.setVisible(false);
		comment.setVisible(false);
		seeComments.setVisible(false);
		submitComment.setVisible(false);
		configuration.setVisible(true);

		remove.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		comment.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		seeComments.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		submitComment.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		configuration.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
	}

	public void showDetailOptions() {
		remove.setVisible(true);
		comment.setVisible(true);
		seeComments.setVisible(true);
		configuration.setVisible(true);
	}

	public void hideDetailOptions() {
		remove.setVisible(false);
		comment.setVisible(false);
		seeComments.setVisible(false);
	}

	public void showSubmitComment() {
		submitComment.setVisible(true);
	}

	public void hideSubmitComment() {
		submitComment.setVisible(false);
	}

}