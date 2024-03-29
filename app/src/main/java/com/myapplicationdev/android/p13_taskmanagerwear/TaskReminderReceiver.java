package com.myapplicationdev.android.p13_taskmanagerwear;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;


public class TaskReminderReceiver extends BroadcastReceiver {

    int notifReqCode = 123;

	@Override
	public void onReceive(Context context, Intent i) {

		int id = i.getIntExtra("id", -1);
		String name = i.getStringExtra("name");
		String desc = i.getStringExtra("desc");

		NotificationManager notificationManager = (NotificationManager)
				context.getSystemService(Context.NOTIFICATION_SERVICE);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel = new
					NotificationChannel("default", "Default Channel",
					NotificationManager.IMPORTANCE_DEFAULT);

			channel.setDescription("This is for default notification");
			notificationManager.createNotificationChannel(channel);
		}

		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(context, notifReqCode,
				intent, PendingIntent.FLAG_CANCEL_CURRENT);

		//Here

		NotificationCompat.Action action = new
				NotificationCompat.Action.Builder(
				R.mipmap.ic_launcher,
				"This is an Action",
				pIntent).build();

		Intent intentreply = new Intent(context, ReplyActivity.class);
		intentreply.putExtra("task",new Task(id,name,desc));
		PendingIntent pendingIntentReply = PendingIntent.getActivity
				(context, 0, intentreply,
						PendingIntent.FLAG_UPDATE_CURRENT);

		RemoteInput ri = new RemoteInput.Builder("status")
				.setLabel("Status Report")
				.setChoices(new String [] {"Completed", "Not yet"})
				.build();

		NotificationCompat.Action action2 = new
				NotificationCompat.Action.Builder(
				R.mipmap.ic_launcher,
				"Complete",
				pendingIntentReply)
				.addRemoteInput(ri)
				.build();

		NotificationCompat.WearableExtender extender = new
				NotificationCompat.WearableExtender();
		extender.addAction(action);
		extender.addAction(action2);






		// build notification
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
		builder.setContentTitle(name);
		builder.setContentText(desc);
		builder.setSmallIcon(android.R.drawable.ic_dialog_info);
		builder.setContentIntent(pIntent);
		builder.setAutoCancel(true);

		builder.extend(extender);

		Notification n = builder.build();

		notificationManager.notify(notifReqCode, n);
	}

}
