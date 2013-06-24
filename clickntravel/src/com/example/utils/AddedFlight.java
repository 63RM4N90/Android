package com.example.utils;


import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.alerts.Alert;
import com.example.alerts.AlertNotification;
import com.example.alerts.ArrivalGateAlert;
import com.example.alerts.ArrivalTerminalAlert;
import com.example.alerts.ArrivalTimeAlert;
import com.example.alerts.BaggageGateAlert;
import com.example.alerts.DepartureGateAlert;
import com.example.alerts.DepartureTerminalAlert;
import com.example.alerts.DepartureTimeAlert;
import com.example.alerts.StatusAlert;
import com.example.utils.FlightStatus;

public class AddedFlight{

	private Destination departure;
	private Destination arrival;
	private Airline airline;
	private int flightId;
	private int flightNumber;
	private List<Alert> alerts;
	private FlightStatus status;

	public AddedFlight(JSONObject status) throws JSONException {

		this.flightId = status.getInt("flightId"); 
		this.flightNumber = status.getInt("number");
		this.status = new FlightStatus(status);
		this.departure = parseDestination(status.getJSONObject("departure"));
		this.arrival = parseDestination(status.getJSONObject("arrival"));
		this.airline = getAirline(status.getJSONObject("airline"));
		this.alerts = parseAlerts(status);

	}
	

	private Destination parseDestination(JSONObject destiny) throws JSONException {
		
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
		return new Airline(airline.getString("id"), airline.getString("name"), airline.getString("logo"));
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
		return status.getStatus();
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
	
	public List<AlertNotification> check(FlightStatus newStatus) {
		List<AlertNotification> notifications = new LinkedList<AlertNotification>();
		for (Alert a : alerts) {
			if (Alert.activeAlerts.get(a.getClass().toString()) && a.changedStatus(status, newStatus))
				notifications.add(a.getNotification(newStatus));
		}
		return notifications;
	}
	
	private List<Alert> parseAlerts(JSONObject json) {
		List<Alert> ret = new LinkedList<Alert>();
		if (json.optBoolean("Status"))
			ret.add(new StatusAlert());
		if (json.optBoolean("DepartureTime"))
			ret.add(new DepartureTimeAlert());
		if (json.optBoolean("DepartureTerminal"))
			ret.add(new DepartureTerminalAlert());
		if (json.optBoolean("DepartureGate"))
			ret.add(new DepartureGateAlert());
		if (json.optBoolean("BaggageGate"))
			ret.add(new BaggageGateAlert());
		if (json.optBoolean("ArrivalTime"))
			ret.add(new ArrivalTimeAlert());
		if (json.optBoolean("ArrivalTerminal"))
			ret.add(new ArrivalTerminalAlert());
		if (json.optBoolean("ArrivalGate"))
			ret.add(new ArrivalGateAlert());
		return ret;
	}
	
}