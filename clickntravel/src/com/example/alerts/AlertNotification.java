package com.example.alerts;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.example.clickntravel.MainActivity;
import com.example.clickntravel.R;

public class AlertNotification {

	private String message;
	private String title;
	public static MainActivity context;
	
	public AlertNotification(String message) {
		this.message = message;
	}
	
	public AlertNotification(String message, String title) {
		this(message);
		this.title = title;
	}
	
	public String toString() {
		return this.message;
	}
	
	public void notifyAlert() {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentTitle(this.title);
        builder.setContentText(this.message);
        Notification notif = builder.build();
        /*Intent resultIntent = new Intent(this, SplashActivity.class);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		
		stackBuilder.addParentStack(SplashActivity.class);
		
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		         stackBuilder.getPendingIntent(
		             0,
		             PendingIntent.FLAG_UPDATE_CURRENT
		         );
		builder.setContentIntent(resultPendingIntent);*/
        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(0, notif);
	}
	
}
