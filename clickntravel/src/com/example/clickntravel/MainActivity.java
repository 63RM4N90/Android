package com.example.clickntravel;

import java.util.List;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.fragments.ConfigurationFragment;
import com.example.handlers.FragmentHandler;
import com.example.utils.FragmentKey;

public class MainActivity extends FragmentActivity implements
		SearchView.OnQueryTextListener {

	private SearchView mSearchView;
	private TextView mStatusView;
	private static final int GROUP_ID = 1;
	private static final int CONFIG_ID = 1;
	private ConfigurationFragment mPrefsFragment = null;
	private FragmentHandler fragmentHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

		ColorDrawable actionBarBackground = new ColorDrawable(Color.rgb(12,
				129, 199));
		ActionBar actionBar = getActionBar();

		setContentView(R.layout.activity_main);

		mStatusView = (TextView) findViewById(R.id.status_text);
		this.fragmentHandler = new FragmentHandler(getSupportFragmentManager());
		this.fragmentHandler.setFragment(FragmentKey.MAIN);
		actionBar.setBackgroundDrawable(actionBarBackground);
		actionBar.setIcon(R.drawable.back);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.searchview_in_menu, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		mSearchView = (SearchView) searchItem.getActionView();
		setupSearchView(searchItem);

		menu.add(GROUP_ID, CONFIG_ID, CONFIG_ID,
				R.string.main_button_configuration);
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
			mPrefsFragment = new ConfigurationFragment();
			FragmentManager mFragmentManager = getFragmentManager();
			FragmentTransaction mFragmentTransaction = mFragmentManager
					.beginTransaction();
			mFragmentTransaction.replace(android.R.id.content, mPrefsFragment);
			mFragmentTransaction.commit();
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
			mPrefsFragment.getPreferenceScreen().removeAll();
			mPrefsFragment = null;
			Log.d("debug", "lalalalalalalala");
		}
		return super.onKeyDown(keyCode, event);
	}
	
// 	Search View

	private void setupSearchView(MenuItem searchItem) {

		if (isAlwaysExpanded()) {
			mSearchView.setIconifiedByDefault(false);
		} else {
			searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
					| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		}

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		if (searchManager != null) {
			List<SearchableInfo> searchables = searchManager
					.getSearchablesInGlobalSearch();

			SearchableInfo info = searchManager
					.getSearchableInfo(getComponentName());
			for (SearchableInfo inf : searchables) {
				if (inf.getSuggestAuthority() != null
						&& inf.getSuggestAuthority().startsWith("applications")) {
					info = inf;
				}
			}
			mSearchView.setSearchableInfo(info);
		}

		mSearchView.setOnQueryTextListener(this);
	}

	public boolean onQueryTextChange(String newText) {
		// mStatusView.setText("Query = " + newText);
		return false;
	}

	public boolean onQueryTextSubmit(String query) {
		// mStatusView.setText("Query = " + query + " : submitted");
		return false;
	}

	public boolean onClose() {
		mStatusView.setText("Closed!");
		return false;
	}

	protected boolean isAlwaysExpanded() {
		return false;
	}

}
