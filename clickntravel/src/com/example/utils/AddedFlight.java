package com.example.utils;


import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class AddedFlight{

	private Destination departure;
	private Destination arrival;
	private Airline airline;
	private int flightId;
	private int flightNumber;
	private String status;
	
//	private NotificationConfiguration config;

	public AddedFlight(JSONObject status) throws JSONException {

		this.flightId = status.getInt("flightId"); 
		this.flightNumber = status.getInt("number");
		this.status = status.getString("status");
		this.departure = getDestination(status.getJSONObject("departure"));
		this.arrival = getDestination(status.getJSONObject("arrival"));
		this.airline = getAirline(status.getJSONObject("airline"));
//		setConfig(new NotificationConfiguration());
		
	}
	
	public AddedFlight(Destination de, Destination ar, Airline ai) {
		departure= de;
		arrival= ar;
		airline= ai;
		flightId= 123;
		flightNumber= flightId;
		status= "puto";
	}

	private Destination getDestination(JSONObject destiny) throws JSONException {
		
		JSONObject airport = destiny.getJSONObject("airport");
		JSONObject city = destiny.getJSONObject("city");
		JSONObject country = destiny.getJSONObject("country");

		return new Destination(airport.getString("id"),
						   airport.getString("description"),
						   airport.getString("terminal"), 
						   airport.getString("gate"),
						   city.getString("name"), 
						   country.getString("name"),
						   destiny.getString("scheduledTime"),
						   destiny.getString("scheduledGateTime"),
						   destiny.getString("actualGateTime"),
						   destiny.getString("estimateRunwayTime"),
						   destiny.getString("actualRunwayTime"));
	}
	
	private Airline getAirline(JSONObject airline) throws JSONException{
		return new Airline(airline.getString("id"),
						   airline.getString("name"),
						   airline.getString("logo")
				);
	}
	
	public Destination getDeparture() {
		return departure;
	}
	
	public Destination getArrival() {
		return arrival;
	}
	
	public Airline getAirline() {
		return airline;
	}
	
	public int getFlightId() {
		return flightId;
	}
	
	public int getFlightNumber() {
		return flightNumber;
	}
	
	public String getFlightStatus() {
		return status;
	}
	
	
	@Override
	public boolean equals(Object o) {
		if (o.getClass() != AddedFlight.class)
			return false;
		return ((AddedFlight)o).flightId == this.flightId;
	}
	
	public String getKey() {
		return String.valueOf(flightId) + airline.getId();
	}

	
	public List<NameValuePair> getParams() {
		List<NameValuePair> params = new LinkedList<NameValuePair> ();
		params.add(new BasicNameValuePair("airline_id", this.airline.getId()));
		params.add(new BasicNameValuePair("flight_num", String.valueOf(this.flightNumber)));
		return params;
	}
	
}