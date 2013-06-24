package com.example.alerts;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.preference.PreferenceManager;

import com.example.utils.FlightStatus;

public abstract class Alert {

	public static Map<String, Boolean> activeAlerts = new HashMap<String, Boolean>();;
	public static long frequency;
	public static Context CONTEXT;
	
	public abstract boolean changedStatus(FlightStatus oldStatus, FlightStatus newStatus);
	
	public abstract AlertNotification getNotification(FlightStatus newStatus);
	
	public abstract String getName();
	
	@SuppressWarnings("unchecked")
	public static void refreshAlerts() {
		Map<String, Boolean> preferencesMap = (Map<String,Boolean>) PreferenceManager.getDefaultSharedPreferences(CONTEXT).getAll();
		activeAlerts.put(BaggageGateAlert.class.toString(), preferencesMap.get("luggageDoorChange"));
		activeAlerts.put(ArrivalGateAlert.class.toString(), preferencesMap.get("doorChange"));
		activeAlerts.put(DepartureGateAlert.class.toString(), preferencesMap.get("doorChange"));
		activeAlerts.put(ArrivalTerminalAlert.class.toString(), preferencesMap.get("terminalChange"));
		activeAlerts.put(DepartureTerminalAlert.class.toString(), preferencesMap.get("terminalChange"));
		activeAlerts.put(ArrivalTimeAlert.class.toString(), preferencesMap.get("hourChange"));
		activeAlerts.put(DepartureTimeAlert.class.toString(), preferencesMap.get("hourChange"));
		activeAlerts.put(StatusAlert.class.toString(), preferencesMap.get("statusChange"));
		frequency = Long.parseLong(PreferenceManager.getDefaultSharedPreferences(CONTEXT).getString("notificationFrequency", "300")) *60;
	}
	
}
