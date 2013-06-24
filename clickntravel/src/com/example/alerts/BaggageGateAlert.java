package com.example.alerts;

import com.example.utils.FlightStatus;

public class BaggageGateAlert extends Alert {

	public boolean changedStatus(FlightStatus oldStatus, FlightStatus newStatus) {
		return !oldStatus.getBaggageGate().equals(newStatus.getBaggageGate());
	}

	public AlertNotification getNotification(FlightStatus newStatus) {
		return new AlertNotification("La nueva puerta de recolección de equipaje es: " + newStatus.getBaggageGate());
	}

	public String getName() {
		return "BaggageGate";
	}
}
