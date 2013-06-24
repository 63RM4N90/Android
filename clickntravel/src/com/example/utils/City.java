package com.example.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class City {

	private String id;
	private String name;

	public City(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public City(JSONObject city) throws JSONException {
		this.id = city.getString("cityId");
		this.name = city.getString("name");
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

}