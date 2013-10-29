package com.example.notifications;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.alerts.Alert;
import com.example.alerts.AlertNotification;
import com.example.api.ApiIntent;
import com.example.api.ApiResultReceiver;
import com.example.api.Callback;
import com.example.utils.AddedFlight;
import com.example.utils.FlightStatus;

public class NotificationService extends IntentService {

	private List<AddedFlight> flightList;
	private final String fileName = "addedFlightsStorage";
	
	public NotificationService() {
		super("NotificationService");
	}
	
	@Override
	protected void onHandleIntent(Intent arg0) {
		while (true) {
			try { Thread.sleep(/*Alert.frequency*/Alert.frequency *1000); }
			catch(InterruptedException e){ }
			try { retrieveData(); } 
			catch(JSONException e){ }
			for (AddedFlight f : flightList) {
					checkStatus(f);
					try { Thread.sleep(2500); }
					catch(InterruptedException e){ }
			}
		}
	}
	
	private void checkStatus(final AddedFlight flight) {
		Callback callback = new Callback() {
			
			public void handleResponse(JSONObject response) {
				FlightStatus currentFlightStatus = null;
				try {
					currentFlightStatus = new FlightStatus(response.getJSONObject("status"));
				} catch (JSONException e) {
					Log.d("json", "invalid status");
				}
				List<AlertNotification> notifs = flight.check(currentFlightStatus);
				for (AlertNotification n : notifs)
					n.notifyAlert();
			}
		};
		ApiResultReceiver receiver = new ApiResultReceiver(null, callback);
		
		ApiIntent intent = new ApiIntent("GetFlightStatus", "Status", receiver, this);
		intent.setParams(flight.getParams());
		startService(intent);
	}
	
	@SuppressWarnings("unchecked")
	private void retrieveData() throws JSONException {
		SharedPreferences prefs = this.getApplicationContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Map<String, String> map = (Map<String,String>)prefs.getAll();
		ArrayList<AddedFlight> fl = new ArrayList<AddedFlight>();
		for (String s: map.values())
			fl.add(new AddedFlight(new JSONObject(s)));
		flightList = fl;
	}
}
