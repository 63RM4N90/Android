package com.example.notifications;

import android.content.Intent;

import com.example.clickntravel.MainActivity;

public class NotificationIntent extends Intent {
	
	public NotificationIntent(MainActivity context){
		super(Intent.ACTION_SYNC, null, context, NotificationService.class);
	}
}
