package com.example.utils;

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
			String arrivalTime, String price, String airlineId) {
		
		this.nameFrom = nameFrom;
		this.nameTo = nameTo;
		this.depTime = depTime;
		this.arrivalTime = arrivalTime;
		this.price = price;
		this.airlineId = airlineId;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result	+ ((arrivalTime == null) ? 0 : arrivalTime.hashCode());
		result = prime * result + ((depTime == null) ? 0 : depTime.hashCode());
		result = prime * result	+ ((flightId == null) ? 0 : flightId.hashCode());
		result = prime * result	+ ((flightNumber == null) ? 0 : flightNumber.hashCode());
		result = prime * result + ((idFrom == null) ? 0 : idFrom.hashCode());
		result = prime * result + ((idTo == null) ? 0 : idTo.hashCode());
		result = prime * result	+ ((nameFrom == null) ? 0 : nameFrom.hashCode());
		result = prime * result + ((nameTo == null) ? 0 : nameTo.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		
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
		Deal other = (Deal) obj;
		
		if (arrivalTime == null) {
			if (other.arrivalTime != null)
				return false;
		} else if (!arrivalTime.equals(other.arrivalTime))
			return false;
		
		if (depTime == null) {
			if (other.depTime != null)
				return false;
		} else if (!depTime.equals(other.depTime))
			return false;
		
		if (flightId == null) {
			if (other.flightId != null)
				return false;
		} else if (!flightId.equals(other.flightId))
			return false;
		
		if (flightNumber == null) {
			if (other.flightNumber != null)
				return false;
		} else if (!flightNumber.equals(other.flightNumber))
			return false;
		
		if (idFrom == null) {
			if (other.idFrom != null)
				return false;
		} else if (!idFrom.equals(other.idFrom))
			return false;
		
		if (idTo == null) {
			if (other.idTo != null)
				return false;
		} else if (!idTo.equals(other.idTo))
			return false;
		
		if (nameFrom == null) {
			if (other.nameFrom != null)
				return false;
		} else if (!nameFrom.equals(other.nameFrom))
			return false;
		
		if (nameTo == null) {
			if (other.nameTo != null)
				return false;
		} else if (!nameTo.equals(other.nameTo))
			return false;
		
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		
		return true;
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
