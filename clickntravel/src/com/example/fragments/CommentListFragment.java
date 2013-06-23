package com.example.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.clickntravel.R;
import com.example.utils.Comment;
import com.example.utils.CommentAdapter;

public class CommentListFragment extends Fragment {

	public static List<Comment> commentList = new ArrayList<Comment>();
	private CommentAdapter adapter;
	private Comment comment;
	private View view;
		
	public CommentListFragment() {
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (view == null) {
			view = inflater.inflate(R.layout.comment_list_fragment, container, false);
		}
		
		ListView listView = (ListView) view.findViewById(R.id.comment_list_view);
		
//		try {
//			retrieveData();
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
		
		adapter = new CommentAdapter(getActivity(), commentList);
		listView.setAdapter(adapter); 
		return view;
	}
	
	public void addComment() {

//		String airlineName = getElementString(R.id.airline_input);
//		if (MyFlightsFragment.airlinesMap == null){
//			Toast.makeText(getActivity(), getActivity().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
//			return;
//		}
//		Airline airline = MyFlightsFragment.airlinesMap.get(airlineName);
//		
//		if (airline == null) {
//			Toast.makeText(getActivity(), getActivity().getString(R.string.invalid_airline), Toast.LENGTH_SHORT).show();
//			return;
//		}
//
//		
//		Callback callback = new Callback() {
//			public void handleResponse(JSONObject response) {
//				try {
//					if (response.has("error")) {
//						Toast.makeText(getActivity().getApplicationContext(),
//								response.getJSONObject("error").getString("message"),
//								Toast.LENGTH_SHORT).show();
//						return;
//					}
//					JSONObject joStatus = response.getJSONObject("status");
//					AddedFlight flight = new AddedFlight(joStatus);
//					if (!flightList.contains(flight)) {
//						storeOnSharedPreferences(joStatus, flight.getKey());
//						flightList.add(flight);
//						adapter.notifyDataSetChanged();
//					} else {
//						Toast.makeText(getActivity(), "Ya estaba el vuelvo ehh guachiiin!!!", Toast.LENGTH_SHORT).show();
//					}
//					eraseField(R.id.flight_number_input);
//					eraseField(R.id.airline_input);
//					
//				} catch (JSONException e) {		}
//			}
//			
//		};
//
//		ApiResultReceiver receiver = new ApiResultReceiver(new Handler(), callback);
//		ApiIntent intent = new ApiIntent("GetFlightStatus", "Status", receiver, this.getActivity());
//		LinkedList<NameValuePair> params = new LinkedList<NameValuePair>();
//		params.add(new BasicNameValuePair("airline_id", airline.getId()));
//		params.add(new BasicNameValuePair("flight_num", getElementString(R.id.flight_number_input)));
//		intent.setParams(params);
//		this.getActivity().startService(intent);
		
	}
	
	private String getElementString(int elementId){
		return ((TextView)getActivity().findViewById(elementId)).getText().toString();
	}

	
//	private void storeOnSharedPreferences(JSONObject favorite, String uniqueKey) {
//		SharedPreferences prefs = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);
//		Editor editor = prefs.edit();
//		editor.putString(uniqueKey, favorite.toString()).commit();
//	}
//	
//	@SuppressWarnings("unchecked")
//	private void retrieveData() throws JSONException {
//		CommentListFragment.flightList = new ArrayList<AddedFlight>();
//		SharedPreferences prefs = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);
//		Map<String, String> map = (Map<String,String>)prefs.getAll();
//		for (String s: map.values()){
//			flightList.add(new AddedFlight(new JSONObject(s)));
//		}
//		prefs = getActivity().getSharedPreferences(preferencesFileName, Context.MODE_PRIVATE);
//		map = (Map<String,String>)prefs.getAll();
//	}
//	
//	private void eraseField(int fieldId) {
//		TextView tv = (TextView) getActivity().findViewById(fieldId);
//		tv.setText("");
//	}
	
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

//	public AddedFlight getCurrentFlight() {
//		return currentFlight;
//	}

}
