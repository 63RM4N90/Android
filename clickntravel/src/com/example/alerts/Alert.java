package com.example.alerts;

import java.util.HashMap;
import java.util.Map;

import com.example.utils.FlightStatus;

public abstract class Alert {

	public static Map<String, Boolean> activeAlerts;
	
	{
		
		activeAlerts = new HashMap<String, Boolean>();
		activeAlerts.put(ArrivalGateAlert.class.toString(), Boolean.valueOf(false));
		activeAlerts.put(ArrivalTerminalAlert.class.toString(), Boolean.valueOf(false));
		activeAlerts.put(ArrivalTimeAlert.class.toString(), Boolean.valueOf(false));
		activeAlerts.put(BaggageGateAlert.class.toString(), Boolean.valueOf(false));
		activeAlerts.put(DepartureGateAlert.class.toString(), Boolean.valueOf(false));
		activeAlerts.put(DepartureTerminalAlert.class.toString(), Boolean.valueOf(false));
		activeAlerts.put(DepartureTimeAlert.class.toString(), Boolean.valueOf(false));
		activeAlerts.put(StatusAlert.class.toString(), Boolean.valueOf(false));
		
	}
	
	public abstract boolean changedStatus(FlightStatus oldStatus, FlightStatus newStatus);
	
	public abstract AlertNotification getNotification(FlightStatus newStatus);
	
	public abstract String getName();
	
}
