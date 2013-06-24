package com.example.utils;

import org.json.JSONException;
import org.json.JSONObject;


public class FlightStatus {

	private int flightId;
	private Airline airline;
	private int number;
	private String status;
	private Checkpoint departure;
	private Checkpoint arrival;
	private JSONObject json;
	
	public FlightStatus(JSONObject json) throws JSONException {
		try {
			this.flightId = json.getInt("flightId");
			this.number = json.getInt("number");
			this.status = json.getString("status");
			this.airline = new Airline(json.getJSONObject("airline"));
			this.departure = new Checkpoint(json.getJSONObject("departure"));
			this.arrival = new Checkpoint(json.getJSONObject("arrival"));
			this.json = json;
		} catch (JSONException e) { 	}
		
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public String getDepartureTime() {
		return this.departure.getTime();
	}

	public String getArrivalTime() {
		return this.arrival.getTime();
	}
	
	public String getDepartureTerminal() {
		return this.departure.getTerminal();
	}
	
	public String getArrivalTerminal() {
		return this.arrival.getTerminal();
	}
	
	public String getArrivalGate() {
		return this.arrival.getGate();
	}
	
	public String getDepartureGate() {
		return this.departure.getGate();
	}
	
	public String getBaggageGate() {
		return this.arrival.getBaggageGate();
	}
	
	public String toString() {
		return json.toString();
	}
}
