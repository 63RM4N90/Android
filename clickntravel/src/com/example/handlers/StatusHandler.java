package com.example.handlers;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Color;

import com.example.clickntravel.R;

public class StatusHandler {
	Map<String, Integer> statusMap = new HashMap<String, Integer>();
	Map<String, Integer> statusColorMap = new HashMap<String, Integer>();
	Map<String, Integer> statusBackgroundColorMap = new HashMap<String, Integer>();
	
	public StatusHandler() {
		statusMap.put("S", R.string.scheduled);
		statusMap.put("A", R.string.active);
		statusMap.put("D", R.string.deviated);
		statusMap.put("L", R.string.landed);
		statusMap.put("C", R.string.cancelled);
		statusColorMap.put("S", Color.rgb(0, 140, 0));
		statusColorMap.put("A", Color.rgb(0, 140, 0));
		statusColorMap.put("D", Color.rgb(255, 201, 38));
		statusColorMap.put("L", Color.rgb(0, 140, 0));
		statusColorMap.put("C", Color.rgb(178, 0, 0));
		statusBackgroundColorMap.put("S", Color.rgb(204, 255, 153));
		statusBackgroundColorMap.put("A", Color.rgb(204, 255, 153));
		statusBackgroundColorMap.put("D", Color.rgb(255, 229, 153));
		statusBackgroundColorMap.put("L", Color.rgb(204, 255, 153));
		statusBackgroundColorMap.put("C", Color.rgb(255, 153, 153));

	}
	
	public int getStatusColor(String status) {
		return statusColorMap.get(status);
	}
	
	public int getStatusBackgroundColor(String status) {
		return statusBackgroundColorMap.get(status);
	}

	public int getStatus(String status) {
		return statusMap.get(status);
	}
}
