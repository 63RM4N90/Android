package com.example.utils;

import org.json.JSONObject;

public class Airport {

	private String id;
	private String description;
	private String timezone;
	private String terminal;
	private String gate;
	private String baggageGate;
	
	public Airport(JSONObject json) {
		this.id = json.optString("id");
		this.description = json.optString("description");
		this.timezone = json.optString("timezone");
		this.terminal = json.optString("terminal");
		this.gate = json.optString("gate");
		this.baggageGate = json.optString("baggageGate");
	}
	
	public String getGate() {
		return this.gate;
	}
	
	public String getTerminal() {
		return this.terminal;
	}
	
	public String getBaggageGate() {
		return this.baggageGate;
	}
	
}
