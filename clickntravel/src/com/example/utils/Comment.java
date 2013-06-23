package com.example.utils;

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
