package com.example.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.clickntravel.R;

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
		airlineId = review.getString("airlineId");
		flightNumber = review.getInt("flightNumber");
		friendlinessRating = review.getInt("friendlinessRating");
		foodRating = review.getInt("foodRating");
		punctualityRating = review.getInt("punctualityRating");
		mileageProgramRating = review.getInt("mileageProgramRating");
		comfortRating = review.getInt("comfortRating");
		qualityPriceRating = review.getInt("qualityPriceRating");
		yesRecommend = review.getInt("yesRecommend");
		comments = review.getString("comments");
		overallRating = friendlinessRating + foodRating + punctualityRating + mileageProgramRating + comfortRating + qualityPriceRating;
		overallRating /= 6;
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
		return (yesRecommend == 1)? R.string.yes : R.string.no;
	}

	public String getComments() {
		return (comments == "null") ? "" : comments;
	}
}
