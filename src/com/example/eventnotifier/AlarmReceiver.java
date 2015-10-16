package com.example.eventnotifier;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class AlarmReceiver extends BroadcastReceiver {
 
	public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        generateNotification( context,  intent);
        Toast.makeText(context, "Its Event time",Toast.LENGTH_LONG).show();
    }
    public void generateNotification(Context context, Intent intent)
    {
        NotificationManager notifManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification note = new Notification(R.drawable.notify1, "New Event", System.currentTimeMillis());

        PendingIntent bintent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);

        note.setLatestEventInfo(context, "Click VIEW for your events", "", bintent);

        notifManager.notify(123, note);
    }

}


