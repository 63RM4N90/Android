package com.example.utils;

import org.json.JSONObject;

public class Checkpoint {

	private Airport airport;
	private String cityId;
	private String cityName;
	private String countryId;
	private String countryName;
	private String scheduledTime;
	private String scheduledGateTime;
	private String actualGateTime;
	private String estimateRunwayTime;
	private String actualRunwayTime;
	private int gateDelay;
	private int runwayDelay;
	
	public Checkpoint(JSONObject json){
		this.airport = new Airport(json.optJSONObject("airport"));
		this.cityId = json.optJSONObject("city").optString("id");
		this.cityName = json.optJSONObject("city").optString("name");
		this.countryId = json.optJSONObject("country").optString("id");
		this.countryName = json.optJSONObject("country").optString("name");
		this.scheduledTime = json.optString("scheduledTime");
		this.scheduledGateTime = json.optString("scheduledGateTime");
		this.actualGateTime = json.optString("actualGateTime");
		this.estimateRunwayTime = json.optString("estimateRunwayTime");
		this.actualRunwayTime = json.optString("actualRunwayTime");
		this.gateDelay = json.optInt("gateDelay");
		this.runwayDelay = json.optInt("runwayDelay");
	}
	
	public String getTime() {
		return this.scheduledTime;
	}
	
	public String getGate() {
		return this.airport.getGate();
	}
	
	public String getTerminal() {
		return this.airport.getTerminal();
	}
	
	public String getBaggageGate() {
		return this.airport.getBaggageGate();
	}
	
}
