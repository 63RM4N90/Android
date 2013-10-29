package com.example.fragments;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.api.ApiIntent;
import com.example.api.ApiResultReceiver;
import com.example.api.Callback;
import com.example.clickntravel.R;
import com.example.utils.Comment;
import com.example.utils.CommentAdapter;

public class CommentListFragment extends Fragment {

	public static List<Comment> commentList;
	private CommentAdapter adapter;
	private View view;
		
	public CommentListFragment() {
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (view == null)
			view = inflater.inflate(R.layout.comment_list_fragment, container, false);

		return view;
	}
	
	@Override
	public void onStart() {
		ActionBar actionBar = getActivity().getActionBar();
		
		commentList = new ArrayList<Comment>();
		refreshCommentList();
		adapter = new CommentAdapter(getActivity(), commentList);
		ListView listView = (ListView) view.findViewById(R.id.comment_list_view);
		listView.setAdapter(adapter); 
		
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		actionBar.setCustomView(R.layout.see_comments_abs_layout);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        super.onStart();
	}
	
	private void refreshCommentList(){
		
		Callback callback = new Callback() {
			public void handleResponse(JSONObject response) {
				try {
					JSONArray reviews = response.getJSONArray("reviews");
					for(int i=0; i < reviews.length(); i++)
						commentList.add(new Comment(reviews.getJSONObject(i)));
					adapter.notifyDataSetChanged();
				} catch (JSONException e) { }
			}
		
		};

	ApiResultReceiver receiver = new ApiResultReceiver(new Handler(), callback);
	ApiIntent intent = new ApiIntent("GetAirlineReviews", "Review", receiver, this.getActivity());
	LinkedList<NameValuePair> params = new LinkedList<NameValuePair>();
	params.add(new BasicNameValuePair("airline_id", FlightListFragment.currentFlight.getAirline().getId()));
	params.add(new BasicNameValuePair("flight_num", FlightListFragment.currentFlight.getFlightNumber() + ""));
	intent.setParams(params);
	this.getActivity().startService(intent);
		
	}

	@Override
	public void onDestroyView() {
		if (view != null)
			((ViewGroup)view.getParent()).removeAllViews();
		ListView lv = (ListView) getActivity().findViewById(R.id.flights_list_view);
		if (lv != null) {
			((ViewGroup)lv.getParent()).removeView(lv);
			lv.removeAllViews();
		}
		super.onDestroyView();
	}

}
