package com.example.utils;

public class Deal {

	private String idFrom;
	private String nameFrom;
	
	private String idTo;
	private String nameTo;
	
	private String price;

	public Deal(String idFrom, String nameFrom, String idTo, String nameTo, String price) {
		
		this.idFrom = idFrom;
		this.nameFrom = nameFrom;
		this.idFrom = idTo;
		this.nameFrom = nameTo;
		this.price = price;
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
}
