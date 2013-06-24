package com.example.alerts;

import com.example.utils.FlightStatus;

public class DepartureGateAlert extends Alert {

	public boolean changedStatus(FlightStatus oldStatus, FlightStatus newStatus) {
		return !oldStatus.getDepartureGate().equals(newStatus.getDepartureGate());
	}

	public AlertNotification getNotification(FlightStatus newStatus) {
		return new AlertNotification("La nueva puerta de embarque es: " + newStatus.getDepartureGate());
	}
	
	public String getName() {
		return "DepartureGate";
	}
}
