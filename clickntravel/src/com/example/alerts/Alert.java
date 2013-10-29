package com.example.alerts;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.preference.PreferenceManager;

import com.example.utils.FlightStatus;

public abstract class Alert {

	public static Map<Alert, Boolean> activeAlerts = new HashMap<Alert, Boolean>();;
	public static long frequency;
	public static Context CONTEXT;
	
	public abstract boolean changedStatus(FlightStatus oldStatus, FlightStatus newStatus);
	
	public abstract AlertNotification getNotification(FlightStatus newStatus);
	
	public abstract String getName();
	
	@SuppressWarnings("unchecked")
	public static void refreshAlerts() {
		activeAlerts.put(new BaggageGateAlert(), PreferenceManager.getDefaultSharedPreferences(CONTEXT).getBoolean("lugggageDoorChange", false));
		activeAlerts.put(new ArrivalGateAlert(), PreferenceManager.getDefaultSharedPreferences(CONTEXT).getBoolean("doorChange", false));
		activeAlerts.put(new DepartureGateAlert(), PreferenceManager.getDefaultSharedPreferences(CONTEXT).getBoolean("doorChange", false));
		activeAlerts.put(new ArrivalTerminalAlert(), PreferenceManager.getDefaultSharedPreferences(CONTEXT).getBoolean("terminalChange", false));
		activeAlerts.put(new DepartureTerminalAlert(), PreferenceManager.getDefaultSharedPreferences(CONTEXT).getBoolean("terminalChange", false));
		activeAlerts.put(new ArrivalTimeAlert(), PreferenceManager.getDefaultSharedPreferences(CONTEXT).getBoolean("hourChange", false));
		activeAlerts.put(new DepartureTimeAlert(), PreferenceManager.getDefaultSharedPreferences(CONTEXT).getBoolean("hourChange", false));
		activeAlerts.put(new StatusAlert(), PreferenceManager.getDefaultSharedPreferences(CONTEXT).getBoolean("statusChange", false));
		frequency = Long.parseLong(PreferenceManager.getDefaultSharedPreferences(CONTEXT).getString("notificationFrequency", "300")) *60;
	}

	public abstract boolean equals(Object obj);
	
	public abstract int hashCode();
}
