package com.example.alerts;

import com.example.utils.FlightStatus;

public interface Alert {

	public boolean changedStatus(FlightStatus oldStatus, FlightStatus newStatus);
	
	public AlertNotification getNotification(FlightStatus newStatus);
	
	public String getName();
}
