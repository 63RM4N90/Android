package com.example.utils;


import org.json.JSONException;
import org.json.JSONObject;

public class Airline {

	private String id;
	private String name;
	
	public Airline(String id, String name, String logoUrl){
		this.id = id;
		this.name = name;
	}
	public Airline(String id, String name){
		this.id = id;
		this.name = name;
	}
	
	public Airline(JSONObject airline) throws JSONException {
		this.id = airline.getString("id");
		this.name = airline.getString("name");
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
}
