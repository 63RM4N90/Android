package com.example.alerts;

import com.example.utils.FlightStatus;

public class ArrivalTimeAlert extends Alert {
	
	public boolean changedStatus(FlightStatus oldStatus, FlightStatus newStatus) {
		return !oldStatus.getArrivalTime().equals(newStatus.getArrivalTime());
	}
	
	public AlertNotification getNotification(FlightStatus newStatus) {
		return new AlertNotification("El nuevo horario del vuelo es: " + newStatus.getArrivalTime());
	}

	public String getName() {
		return "ArrivalTime";
	}
}
