package com.example.alerts;

import com.example.utils.FlightStatus;

public class ArrivalTerminalAlert extends Alert {

	public boolean changedStatus(FlightStatus oldStatus, FlightStatus newStatus) {
		return !oldStatus.getArrivalTerminal().equals(newStatus.getArrivalTerminal());
	}

	public AlertNotification getNotification(FlightStatus newStatus) {
		return new AlertNotification("La nueva terminal es la: " + newStatus.getArrivalTerminal());
	}
	
	public String getName() {
		return "ArrivalTerminal";
	}

}
