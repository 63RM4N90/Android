package com.example.utils;

import android.app.Activity;

import com.example.clickntravel.R;

public class Destination {

	private String airportId, airportDescription, cityName, countryName,
			scheduledTime, scheduledGateTime, actualGateTime,
			estimateRunwayTime, actualRunwayTime, airportTerminal, airportGate;

	public Destination(String aId, String aDsc, String aTerm, String aGate,
			String ciName, String coName, String schTime, String schGTime,
			String actGTime, String estRunTime, String actRunTime) {

		this.airportId = aId;
		this.airportDescription = aDsc;
		this.airportTerminal = aTerm;
		this.airportGate = aGate;
		this.cityName = ciName;
		this.countryName = coName;
		this.scheduledTime = schTime;
		this.scheduledGateTime = schGTime;
		this.actualGateTime = actGTime;
		this.estimateRunwayTime = estRunTime;
		this.actualRunwayTime = actRunTime;
		
	}

	public String getAirportId() {
		return airportId;
	}

	public String getAirportDescription() {
		return airportDescription;
	}

	public String getCityName() {
		return cityName;
	}

	public String getCountryName() {
		return countryName;
	}

	public String getScheduledTime() {
		return scheduledTime;
	}

	public String getScheduledGateTime() {
		return scheduledGateTime;
	}

	public String getActualGateTime() {
		return actualGateTime;
	}

	public String getEstimateRunwayTime() {
		return estimateRunwayTime;
	}

	public String getActualRunwayTime() {
		return actualRunwayTime;
	}

	public String getAirportTerminal(Activity a) {
		return ((airportTerminal != "null") ? airportTerminal : a.getText(R.string.n_a).toString());
	}

	public String getAirportGate(Activity a) {
		return ((airportGate != "null") ? airportGate : a.getText(R.string.n_a).toString());
	}

}

