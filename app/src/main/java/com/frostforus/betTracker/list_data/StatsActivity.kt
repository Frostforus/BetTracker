package com.frostforus.betTracker.list_data

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.frostforus.betTracker.R
import com.frostforus.betTracker.list_data.data.BetListDatabase
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_stats.*
import kotlin.concurrent.thread


class StatsActivity : AppCompatActivity() {
    private lateinit var database: BetListDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        //TODO: clean logcat
        Log.i("Event_info", "StatsActivity loaded")
        database = Room.databaseBuilder(
            applicationContext,
            BetListDatabase::class.java,
            "bet-list"
        ).fallbackToDestructiveMigration().build()
        loadStats()
    }

    private fun loadStats() {
        thread {
            val betsOver = database.betItemDao().getBetsOver()
            val betsNotOver = database.betItemDao().getBetsNotOver()
            val entries = listOf(
                //TODO: get this shit to work

                PieEntry(betsOver.toFloat(), "Vagine"),
                PieEntry(betsNotOver.toFloat(), "XDDDDD"),
                PieEntry(3.toFloat(), "Penis")
            )

            val dataSet = PieDataSet(entries, "BRUNCHOS")
            dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

            val data = PieData(dataSet)
            chartStats.data = data

            //Disable random ass description
            chartStats.description.isEnabled = false
            chartStats.invalidate()
        }

    }
}