package com.example.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class Deal {

	private String idFrom;
	private String nameFrom;

	private String idTo;
	private String nameTo;

	private String depTime;
	private String arrivalTime;

	private String price;

	private String airlineId;

	private String flightId;
	private String flightNumber;

	public Deal(String nameFrom, String nameTo, String depTime,
			String arrivalTime, String price) {

		this.nameFrom = nameFrom;
		this.nameTo = nameTo;
		this.depTime = depTime;
		this.arrivalTime = arrivalTime;
		this.price = price;
	}

	public Deal(String idFrom, String nameFrom, String idTo, String nameTo,
			String price, String airlineId, String flightId,
			String flightNumber, String depTime, String arrivalTime) {

		this.idFrom = idFrom;
		this.nameFrom = nameFrom;
		this.idTo = idTo;
		this.nameTo = nameTo;
		this.price = price;
		this.airlineId = airlineId;
		this.flightId = flightId;
		this.flightNumber = flightNumber;
		this.depTime = depTime;
		this.arrivalTime = arrivalTime;
	}

	public Deal(JSONObject status) throws JSONException {

		this.idFrom = idFrom;
		this.nameFrom = nameFrom;
		this.idTo = idTo;
		this.nameTo = nameTo;
		this.price = price;
		// this.airlineId = getAirline(status.getJSONObject("airline"));
		// ;
		// this.flightId = status.getInt("flightId");
		// this.flightNumber = status.getInt("number");
		// this.depTime = parseDestination(status.getJSONObject("departure"));
		// this.arrivalTime = parseDestination(status.getJSONObject("arrival"));
	}

	private Destination parseDestination(JSONObject destiny)
			throws JSONException {

		JSONObject airport = destiny.getJSONObject("airport");
		JSONObject city = destiny.getJSONObject("city");
		JSONObject country = destiny.getJSONObject("country");

		return new Destination(airport.getString("id"),
				airport.getString("description"),
				airport.getString("terminal"), airport.getString("gate"),
				city.getString("name"), country.getString("name"),
				destiny.getString("scheduledTime"),
				destiny.getString("scheduledGateTime"),
				destiny.getString("actualGateTime"),
				destiny.getString("estimateRunwayTime"),
				destiny.getString("actualRunwayTime"));
	}

	private Airline getAirline(JSONObject airline) throws JSONException {
		return new Airline(airline.getString("id"), airline.getString("name"),
				airline.getString("logo"));
	}

	public String getDepTime() {
		return depTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public String getFlightId() {
		return flightId;
	}

	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getAirlineId() {
		return airlineId;
	}

	public String getIdFrom() {

		return idFrom;
	}

	public String getNameFrom() {

		return nameFrom;
	}

	public String getIdTo() {

		return idTo;
	}

	public String getNameTo() {

		return nameTo;
	}

	public String getPrice() {

		return price;
	}

	public String toString() {

		return idFrom + nameFrom + idTo + nameTo + price + " " + airlineId
				+ " " + flightId + " " + flightNumber + "" + depTime + ""
				+ arrivalTime;
	}
}
