package com.example.alerts;

import com.example.utils.FlightStatus;

public class StatusAlert extends Alert {

	public boolean changedStatus(FlightStatus oldStatus,
			FlightStatus newStatus) {
		return !oldStatus.getStatus().equals(newStatus.getStatus());
	}

	public AlertNotification getNotification(FlightStatus newStatus) {
		return new AlertNotification("El nuevo estado es: " + newStatus.getStatus(), "Estado del vuelo");
	}
	
	public String getName() {
		return "Status";
	}

}
