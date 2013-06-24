package com.example.api;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.example.fragments.FlightListFragment;

import android.app.IntentService;
import android.content.Intent;

public class PostService extends IntentService {
	public static int friendlinessRating;
	public static int foodRating;
	public static int punctualityRating;
	public static int mileageProgramRating;
	public static int comfortRating;
	public static int qualityPriceRating;
	public static boolean yesRecommend;
	public static String comments;
	
	public PostService() {
		super("PostService");
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost put = new HttpPost("http://eiffel.itba.edu.ar/hci/service2/Review.groovy?method=ReviewAirline");
		JSONObject data = new JSONObject();
		try {
			data.accumulate("airlineId", FlightListFragment.currentFlight.getAirline().getId());
			data.accumulate("flightNumber", FlightListFragment.currentFlight.getFlightNumber());
			data.accumulate("friendlinessRating", friendlinessRating);
			data.accumulate("foodRating", foodRating);
			data.accumulate("punctualityRating", punctualityRating);
			data.accumulate("mileageProgramRating", mileageProgramRating);
			data.accumulate("comfortRating", comfortRating);
			data.accumulate("qualityPriceRating", qualityPriceRating);
			data.accumulate("yesRecommend", yesRecommend);
			data.accumulate("comments", comments);
			StringEntity entity = new StringEntity(data.toString());
			entity.setContentType("application/json");
			put.setEntity(entity);
			httpClient.execute(put);
		} catch(Exception e) { }
	}

}
