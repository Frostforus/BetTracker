package com.frostforus.betTracker.Settings

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.frostforus.betTracker.R
import com.frostforus.betTracker.list_data.broadcastreceiver.NotificationService
import com.frostforus.betTracker.list_data.data.BetListDatabase
import com.frostforus.betTracker.list_data.data.NameItemSingleton
import kotlinx.android.synthetic.main.activity_settings.*
import kotlin.concurrent.thread


class SettingsActivity : AppCompatActivity() {

    private var FIRSTFLAG: Boolean = true
    private lateinit var database: BetListDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        btn_toggle_notifications.setOnClickListener {
            Log.v("SERVICE", "Button hit")
            if (FIRSTFLAG) {
                Log.v("SERVICE", "First time")
                if (isMyServiceRunning(NotificationService::class.java)) {
                    Log.v("SERVICE", "It was on")
                    btn_toggle_notifications.text = getString(R.string.turn_off_notifications)
                } else {
                    Log.v("SERVICE", "It was off")
                    btn_toggle_notifications.text = getString(R.string.turn_on_notifications)
                }
                FIRSTFLAG = false
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

        new_name_button.setOnClickListener {
            database = Room.databaseBuilder(
                applicationContext,
                BetListDatabase::class.java,
                "bet-list"
            ).fallbackToDestructiveMigration().build()

            changeName(edittext_new_name.text.toString())

        }
    }

    private fun changeName(newName: String) {
        thread {
            Log.v("Changing name to:", newName)

            val newItem = NameItemSingleton(1, newName.replace(" ", "_"))

            if (database.NameItemSingletonDao().getName().isNullOrEmpty()) {
                database.NameItemSingletonDao().insert(newItem)
            } else {
                database.NameItemSingletonDao().update(newItem)
            }
            Log.v("Changed name to", database.NameItemSingletonDao().getName()[0].toString())

        }
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager: ActivityManager =
            getSystemService(Context.ACTIVITY_SERVICE) as (ActivityManager)

        for (service: ActivityManager.RunningServiceInfo in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }


}
