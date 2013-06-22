package com.example.notifications;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;


import com.example.alerts.AlertNotification;
import com.example.api.ApiIntent;
import com.example.api.ApiResultReceiver;
import com.example.api.Callback;
import com.example.clickntravel.MainActivity;
import com.example.utils.AddedFlight;
import com.example.utils.FlightStatus;

public class NotificationService extends IntentService {

	private static long seconds = 300;
	private List<AddedFlight> flightList = new ArrayList<AddedFlight>();	
	private ApiResultReceiver receiver;
	
	public NotificationService() {
		super("NotificationService");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
//		this.favourites = MainActivity.favourites;
	    return super.onStartCommand(intent,flags,startId);
	}
	
	@Override
	protected void onHandleIntent(Intent arg0) {
		Log.d("service", "entro");
		while (true) {
			try {
				synchronized (this) {
					wait(seconds * 1000);
					for (AddedFlight f : flightList) {
							Log.d("service", f.getFlightNumber() + "");
							checkStatus(f);
							wait(1000);
					}
				}
			} catch (Exception e) { 
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
				if (notifs.isEmpty())
					Log.d("notif", "no hay notifs" + flight.getFlightNumber());
				for (AlertNotification n : notifs)
					n.notifyAlert();
			}
		};
		receiver = new ApiResultReceiver(new Handler(), callback);
		ApiIntent intent = new ApiIntent("GetFlightStatus", "Status",
				this.receiver, this);
		intent.setParams(flight.getParams());
		startService(intent);
	}
}
