package com.example.fragments;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.api.PostService;
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
