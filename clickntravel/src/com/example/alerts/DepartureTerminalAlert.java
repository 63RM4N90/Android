package com.example.alerts;

import com.example.utils.FlightStatus;

public class DepartureTerminalAlert extends Alert {

	public boolean changedStatus(FlightStatus oldStatus, FlightStatus newStatus) {
		return !oldStatus.getDepartureTerminal().equals(newStatus.getDepartureTerminal());
	}

	public AlertNotification getNotification(FlightStatus newStatus) {
		return new AlertNotification("La nueva terminal de embarque es la: " + newStatus.getDepartureTerminal());
	}

	public String getName() {
		return "DepartureTerminal";
	}
}
