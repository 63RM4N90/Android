package com.example.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class Comment {
	String airlineId;
	int flightNumber;
	int overallRating;
	int friendlinessRating;
	int foodRating;
	int punctualityRating;
	int mileageProgramRating;
	int comfortRating;
	int qualityPriceRating;
	int yesRecommend;
	String comments;
	
	public Comment(	String airlineId, int flightNumber,	int overallRating, int friendlinessRating, int foodRating,
			int punctualityRating, int mileageProgramRating, int comfortRating, int qualityPriceRating, int yesRecommend, String comments){
		this.airlineId = airlineId;
		this.flightNumber = flightNumber;
		this.overallRating = overallRating;
		this.friendlinessRating = friendlinessRating;
		this.foodRating = foodRating;
		this.punctualityRating = punctualityRating;
		this.mileageProgramRating = mileageProgramRating;
		this.comfortRating = comfortRating;
		this.qualityPriceRating = qualityPriceRating;
		this.yesRecommend = yesRecommend;
		this.comments = comments;
	}
	
	public Comment(JSONObject review) {
		try {
		this.airlineId = review.getString("airlineId");
		this.flightNumber = review.getInt("flightNumber");
		this.overallRating = review.getInt("overallRating");
		this.friendlinessRating = review.getInt("friendlinessRating");
		this.foodRating = review.getInt("foodRating");
		this.punctualityRating = review.getInt("punctualityRating");
		this.mileageProgramRating = review.getInt("mileageProgramRating");
		this.comfortRating = review.getInt("comfortRating");
		this.qualityPriceRating = review.getInt("qualityPriceRating");
		this.yesRecommend = review.getInt("yesRecommend");
		this.comments = review.getString("comments");
		} catch(JSONException e) { }
	}

	public String getAirlineId() {
		return airlineId;
	}

	public int getFlightNumber() {
		return flightNumber;
	}

	public int getOverallRating() {
		return overallRating;
	}

	public int getFriendlinessRating() {
		return friendlinessRating;
	}

	public int getFoodRating() {
		return foodRating;
	}

	public int getPunctualityRating() {
		return punctualityRating;
	}

	public int getMileageProgramRating() {
		return mileageProgramRating;
	}

	public int getComfortRating() {
		return comfortRating;
	}

	public int getQualityPriceRating() {
		return qualityPriceRating;
	}

	public int getYesRecommend() {
		return yesRecommend;
	}

	public String getComments() {
		return comments;
	}
}
