package com.example.utils;

public class Flight {

	private String idFrom;
	private String nameFrom;
	
	private String idTo;
	private String nameTo;
	
	private String price;

	private String airlineId;
	private String logoUrl;
	
	public Flight(String idFrom, String nameFrom, String idTo, String nameTo, String price,
			String airlineId, String LogoUrl) {
		
		this.idFrom = idFrom;
		this.nameFrom = nameFrom;
		this.idFrom = idTo;
		this.nameFrom = nameTo;
		this.price = price;
	}
}
