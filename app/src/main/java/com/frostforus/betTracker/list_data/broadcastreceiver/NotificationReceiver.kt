package com.frostforus.betTracker.list_data.broadcastreceiver

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.frostforus.betTracker.list_data.BetActivity

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val repeatingIntent = Intent(context, BetActivity::class.java)
        repeatingIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(
            context,
            100,
            repeatingIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, "Main")
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.arrow_down_float)
                .setContentTitle("Notification Every Day")
                .setContentText("Text here")
                .setAutoCancel(true)
        notificationManager.notify(100, builder.build())
    }

}