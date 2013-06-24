package com.example.fragments;

import java.net.URLEncoder;
import java.util.LinkedList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.example.api.PostService;
import com.example.clickntravel.MainActivity;
import com.example.clickntravel.R;
import com.example.utils.AddedFlight;

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
		Intent intent = new Intent(Intent.ACTION_SYNC, null, getActivity(), PostService.class);
		PostService.friendlinessRating = getRatingFrom(R.id.kindness_rating);
		PostService.foodRating = getRatingFrom(R.id.food_rating);
		PostService.punctualityRating = getRatingFrom(R.id.punctuality_rating);
		PostService.mileageProgramRating = getRatingFrom(R.id.millage_program_rating);
		PostService.comfortRating = getRatingFrom(R.id.comfort_rating);
		PostService.qualityPriceRating = getRatingFrom(R.id.price_quality_relation_rating);
		PostService.yesRecommend = getValueFromSwitch(R.id.yes_no_recommend);
		PostService.comments = getStringFromField(R.id.editText1);
		getActivity().startService(intent);
	}
	
//	Callback callback = new Callback() {
//		public void handleResponse(JSONObject response) {
//			Log.d("RESPONDIO", response + "");
//		}
//		
//	};
//	ApiResultReceiver receiver = new ApiResultReceiver(new Handler(), callback);
//	ApiIntent intent = new ApiIntent("ReviewAirline2", "Review", receiver, this.getActivity());
//	JSONObject data = new JSONObject();
//	try {
//	data.accumulate("airlineId", FlightListFragment.currentFlight.getAirline().getId());
//	data.accumulate("flightNumber", FlightListFragment.currentFlight.getFlightNumber() + "");
//	data.accumulate("friendlinessRating", getRatingFrom(R.id.kindness_rating) + "");
//	data.accumulate("foodRating", getRatingFrom(R.id.food_rating) + "");
//	data.accumulate("punctualityRating", getRatingFrom(R.id.punctuality_rating) + "");
//	data.accumulate("mileageProgramRating", getRatingFrom(R.id.millage_program_rating) + "");
//	data.accumulate("comfortRating", getRatingFrom(R.id.comfort_rating) + "");
//	data.accumulate("qualityPriceRating", getRatingFrom(R.id.price_quality_relation_rating) + "");
//	data.accumulate("yesRecommend", getValueFromSwitch(R.id.yes_no_recommend) + "");
//	data.accumulate("comments", getStringFromField(R.id.editText1));
//
//	LinkedList<NameValuePair> params = new LinkedList<NameValuePair>();
//	params.add(new BasicNameValuePair("data", data.toString()));
//	intent.setParams(params);
//	this.getActivity().startService(intent);
//	} catch(Exception e) { Log.d("EXCEPCION", "TE CABIO GIL" + e); }
	
//	Intent intent = new Intent(Intent.ACTION_SYNC, null, getActivity(), PostService.class);
//	PostService.friendlinessRating = getRatingFrom(R.id.kindness_rating) + "";
//	PostService.foodRating = getRatingFrom(R.id.food_rating) + "";
//	PostService.punctualityRating = getRatingFrom(R.id.punctuality_rating) + "";
//	PostService.mileageProgramRating = getRatingFrom(R.id.millage_program_rating) + "";
//	PostService.comfortRating = getRatingFrom(R.id.comfort_rating) + "";
//	PostService.qualityPriceRating = getRatingFrom(R.id.price_quality_relation_rating) + "";
//	PostService.yesRecommend = getValueFromSwitch(R.id.yes_no_recommend) + "";
//	PostService.comments = getStringFromField(R.id.editText1);
//	getActivity().startService(intent);


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
