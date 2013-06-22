package com.example.alerts;

import com.example.utils.FlightStatus;

public class ArrivalTimeAlert implements Alert {
	
	public boolean changedStatus(FlightStatus oldStatus, FlightStatus newStatus) {
		return !oldStatus.getArrivalTime().equals(newStatus.getArrivalTime());
	}
	
	public AlertNotification getNotification(FlightStatus newStatus) {
		return new AlertNotification("El nuevo horario de llegada es: " + newStatus.getArrivalTime());
	}

	public String getName() {
		return "ArrivalTime";
	}
}
