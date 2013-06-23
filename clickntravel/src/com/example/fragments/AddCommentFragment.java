package com.example.fragments;



import java.util.LinkedList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api.ApiIntent;
import com.example.api.ApiResultReceiver;
import com.example.api.Callback;
import com.example.clickntravel.MainActivity;
import com.example.clickntravel.R;

public class AddCommentFragment extends Fragment {
	
	public AddCommentFragment() {
		/*empty constructor*/
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ActionBar actionBar = getActivity().getActionBar();
		View view = inflater.inflate(R.layout.comments_fragment, container, false);
		
		((MainActivity) getActivity()).showSubmitComment();
		
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		actionBar.setCustomView(R.layout.add_comment_abs_layout);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
		
		return view;
		
	}
	
	public void addComment() {
		
		Callback callback = new Callback() {
			public void handleResponse(JSONObject response) {
						Toast.makeText(getActivity(), "BANDA DE AGREGADO", Toast.LENGTH_SHORT).show();
			}
			
		};

		ApiResultReceiver receiver = new ApiResultReceiver(new Handler(), callback);
		ApiIntent intent = new ApiIntent("ReviewAirline", "Review", receiver, this.getActivity());
		LinkedList<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("airlineId", FlightListFragment.currentFlight.getAirline().getId()));
		params.add(new BasicNameValuePair("flightNumber", FlightListFragment.currentFlight.getFlightNumber() + ""));
		params.add(new BasicNameValuePair("friendlinessRating", getRatingFrom(R.id.kindness_rating) + ""));
		params.add(new BasicNameValuePair("foodRating", getRatingFrom(R.id.food_rating) + ""));
		params.add(new BasicNameValuePair("punctualityRating", getRatingFrom(R.id.punctuality_rating) + ""));
		params.add(new BasicNameValuePair("mileageProgramRating", getRatingFrom(R.id.millage_program_rating) + ""));
		params.add(new BasicNameValuePair("comfortRating", getRatingFrom(R.id.comfort_rating) + ""));
		params.add(new BasicNameValuePair("qualityPriceRating", getRatingFrom(R.id.price_quality_relation_rating) + ""));
		params.add(new BasicNameValuePair("yesRecommend", getValueFromSwitch(R.id.yes_no_recommend) + ""));
		params.add(new BasicNameValuePair("comments", getStringFromField(R.id.editText1) + ""));
		intent.setParams(params);
		this.getActivity().startService(intent);
		
	}


	private String getStringFromField(int fieldId){
		return ((TextView)getActivity().findViewById(fieldId)).getText().toString();
	}
	
	private int getRatingFrom(int ratingBarId) {
		RatingBar rb = (RatingBar) getActivity().findViewById(ratingBarId);
		return Math.round((rb.getRating() * 8 / 5)) + 1;
	}
	
	private boolean getValueFromSwitch(int switchId) {
		Switch sw = (Switch) getActivity().findViewById(switchId);
		return sw.isChecked();
	}
	
}
