package com.example.utils;

public class Deal {

	private String idFrom;
	private String nameFrom;

	private String idTo;
	private String nameTo;

	private String price;

	private String airlineId;

	private String flightId;
	private String flightNumber;

	public Deal(String idFrom, String nameFrom, String idTo, String nameTo,
			String price, String airlineId, String flightId, String flightNumber) {

		this.idFrom = idFrom;
		this.nameFrom = nameFrom;
		this.idTo = idTo;
		this.nameTo = nameTo;
		this.price = price;
		this.airlineId = airlineId;
		this.flightId = flightId;
		this.flightNumber = flightNumber;
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
				+ " " + flightId + " " + flightNumber;
	}
}
