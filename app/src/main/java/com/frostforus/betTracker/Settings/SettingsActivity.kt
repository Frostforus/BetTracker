package com.frostforus.betTracker.Settings

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.frostforus.betTracker.R
import com.frostforus.betTracker.list_data.broadcastreceiver.NotificationService
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity() {

    private var FIRST_FLAG: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        btn_toggle_notifications.setOnClickListener {
            Log.v("SERVICE", "Button hit")
            if (FIRST_FLAG) {
                Log.v("SERVICE", "First time")
                if (isMyServiceRunning(NotificationService::class.java)) {
                    Log.v("SERVICE", "It was on")
                    btn_toggle_notifications.text = getString(R.string.turn_off_notifications)
                } else {
                    Log.v("SERVICE", "It was off")
                    btn_toggle_notifications.text = getString(R.string.turn_on_notifications)
                }
                FIRST_FLAG = false
            } else {
                Log.v("SERVICE", "Not the first time")
                if (isMyServiceRunning(NotificationService::class.java)) {
                    Log.v("SERVICE", "It was on now turning off")
                    stopService(Intent(this@SettingsActivity, NotificationService::class.java))
                    btn_toggle_notifications.text = getString(R.string.turn_on_notifications)
                } else {
                    Log.v("SERVICE", "It was off now turning on")
                    startService(Intent(this@SettingsActivity, NotificationService::class.java))
                    btn_toggle_notifications.text = getString(R.string.turn_off_notifications)
                }
            }


        }
    }

    fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager: ActivityManager =
            getSystemService(Context.ACTIVITY_SERVICE) as (ActivityManager);

        for (service: ActivityManager.RunningServiceInfo in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true;
            }
        }
        return false;
    }


}
