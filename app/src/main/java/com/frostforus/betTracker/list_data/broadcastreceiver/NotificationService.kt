package com.frostforus.betTracker.list_data.broadcastreceiver

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.frostforus.betTracker.R
import java.util.*


class NotificationService : Service() {
    var timer: Timer? = null
    var timerTask: TimerTask? = null
    var TAG = "SERVICE"
    var Your_X_SECS: Long = 5
    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.v(TAG, "onStartCommand")
        super.onStartCommand(intent, flags, startId)
        startTimer()
        return START_STICKY
    }

    override fun onCreate() {
        Log.v(TAG, "onCreate")
    }

    override fun onDestroy() {
        Log.v(TAG, "onDestroy")
        stopTimerTask()
        super.onDestroy()
    }

    //we are going to use a handler to be able to run in our TimerTask
    val handler: Handler = Handler()
    fun startTimer() {
        timer = Timer()
        initializeTimerTask()
        timer!!.schedule(timerTask, 5000, Your_X_SECS * 1000) //
    }

    fun stopTimerTask() {
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }

    fun initializeTimerTask() {
        timerTask = object : TimerTask() {
            override fun run() {
                handler.post(Runnable { createNotification() })
            }
        }
    }

    private fun createNotification() {
        Log.v(TAG, "Notification Created")
        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, default_notification_channel_id)
        mBuilder.setContentTitle("Bet Tracker")
        mBuilder.setContentText("Check Your Bets to see if any of them are over! \n (This is only this fast for show)")
        mBuilder.setTicker("Notification Listener Service Example")
        mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground)
        mBuilder.setAutoCancel(true)


        mNotificationManager.notify(System.currentTimeMillis().toInt(), mBuilder.build())
    }

    companion object {

        private const val default_notification_channel_id = "default"
    }
}
