package com.example.handlers;

import java.util.HashMap;
import java.util.Map;

import com.example.clickntravel.R;




public class ImageHandler {
	Map<String, Integer> imageMap = new HashMap<String, Integer>();
	
	public ImageHandler(){
		imageMap.put("Aerolineas Argentinas", R.drawable.ar);
		imageMap.put("Lan",R.drawable.la);
		imageMap.put("SOL", R.drawable.sol);
		imageMap.put("TAM", R.drawable.jj);
		imageMap.put("American Airlines",R.drawable.aa);
		imageMap.put("Air France", R.drawable.af);
		imageMap.put("AeroMexico", R.drawable.am);
		imageMap.put("Avianca",R.drawable.av);
		imageMap.put("Alitalia", R.drawable.az);
		imageMap.put("British Airways", R.drawable.ba);
		imageMap.put("Copa Airlines", R.drawable.cm);
		imageMap.put("Iberia",R.drawable.ib);
		imageMap.put("Taca", R.drawable.ta);
	}
	
	public int getImage(String airline) {
		return imageMap.get(airline);
	}
}
