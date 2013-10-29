package com.example.alerts;

import com.example.clickntravel.MainActivity;
import com.example.utils.FlightStatus;

public class ArrivalGateAlert extends Alert {

	String name = "ArrivalGate";
	
	public boolean changedStatus(FlightStatus oldStatus, FlightStatus newStatus) {
		return !oldStatus.getArrivalGate().equals(newStatus.getArrivalGate());
	}

	public AlertNotification getNotification(FlightStatus newStatus) {
		return new AlertNotification(MainActivity.NEW_ARRIVAL_GATE + " "
				+ newStatus.getArrivalGate());
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArrivalGateAlert other = (ArrivalGateAlert) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}