package com.frostforus.betTracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.frostforus.betTracker.list_data.BetActivity
import com.frostforus.betTracker.list_data.StatsActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"

    private fun startBetActivity() {
        startActivity(Intent(this, BetActivity::class.java))
        Log.v("$TAG.startBetActivity()", "Function called.")
    }

    private fun startStatActivity() {
        startActivity(Intent(this, StatsActivity::class.java))
        Log.v("$TAG.startStatActivity()", "Function called.")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // This is a higher level function overrides current one
        btn_my_bets.setOnClickListener {
            startBetActivity()
        }

        btn_my_stats.setOnClickListener {
            startStatActivity()
        }

        btn_settings.setOnClickListener {
            //TODO: IMPLEMENT
        }
    }
}