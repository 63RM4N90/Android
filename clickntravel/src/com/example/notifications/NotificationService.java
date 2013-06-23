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
import android.os.Handler;
import android.util.Log;

import com.example.alerts.Alert;
import com.example.alerts.AlertNotification;
import com.example.api.ApiIntent;
import com.example.api.ApiResultReceiver;
import com.example.api.Callback;
import com.example.utils.AddedFlight;
import com.example.utils.FlightStatus;

public class NotificationService extends IntentService {

	private List<AddedFlight> flightList = new ArrayList<AddedFlight>();	
	private ApiResultReceiver receiver;
	private final String fileName = "addedFlightsStorage";
	
	public NotificationService() {
		super("NotificationService");
	}
	
	@Override
	protected void onHandleIntent(Intent arg0) {
		try { retrieveData(); } 
		catch(JSONException e){ }
		while (true) {
			try { Thread.sleep(Alert.frequency *1000); }
			catch(InterruptedException e){ }
			for (AddedFlight f : flightList)
					checkStatus(f);
		}
	}
	
	private void checkStatus(final AddedFlight flight) {
		Callback callback = new Callback() {
			
			public void handleResponse(JSONObject response) {
				FlightStatus currentFlightStatus = null;
				Log.d(" ", "QUE ONDA, ENTRAS ACA???");
				try {
					currentFlightStatus = new FlightStatus(response.getJSONObject("status"));
				} catch (JSONException e) {
					Log.d("json", "invalid status");
				}
				List<AlertNotification> notifs = flight.check(currentFlightStatus);
				if (notifs.isEmpty())
					Log.d("notif", "no hay notifs" + flight.getFlightNumber());
				for (AlertNotification n : notifs)
					n.notifyAlert();
			}
		};
		receiver = new ApiResultReceiver(new Handler(), callback);
		ApiIntent intent = new ApiIntent("GetFlightStatus", "Status", receiver, this);
		intent.setParams(flight.getParams());
		startService(intent);
	}
	
	@SuppressWarnings("unchecked")
	private void retrieveData() throws JSONException {
		SharedPreferences prefs = this.getApplicationContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Map<String, String> map = (Map<String,String>)prefs.getAll();
		for (String s: map.values()){
			flightList.add(new AddedFlight(new JSONObject(s)));
		}
	}
}
