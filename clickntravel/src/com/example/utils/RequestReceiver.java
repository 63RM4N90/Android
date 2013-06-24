package com.example.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

public abstract class RequestReceiver extends ResultReceiver {

	public RequestReceiver() {
		super(new Handler());
	}

	public abstract void onStarted();
	
	public abstract void onData(String data);
	
	public abstract void onError(String error);

	@Override
	protected void onReceiveResult(int resultCode, Bundle resultData) {
		
		switch(resultCode){
		case FlightsAPIService.STARTED:
			onStarted();
			break;
		case FlightsAPIService.DONE:
			if (resultData.containsKey("error")){
				onError(resultData.getString("error"));
				return;
			}
			onData(resultData.getString("response"));
			break;
		}
	}
	
}
