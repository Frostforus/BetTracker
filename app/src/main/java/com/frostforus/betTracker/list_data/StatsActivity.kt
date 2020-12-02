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
            val betsLost = database.betItemDao().getBetsLost()
            val betsWon = database.betItemDao().getBetsWon()
            val betsInProgress = database.betItemDao().getBetsInProgress()
            Log.v("STATS", betsLost.toString())
            Log.v("STATS", betsWon.toString())
            Log.v("STATS", betsInProgress.toString())
            val entries = if (betsLost == 0 && betsWon == 0 && betsInProgress == 0) {
                listOf(
                    PieEntry(1.toFloat(), getString(R.string.nodata))
                )
            } else {
                listOf(
                    PieEntry(betsInProgress.toFloat(), getString(R.string.inprogress)),
                    PieEntry(betsWon.toFloat(), getString(R.string.won)),
                    PieEntry(betsLost.toFloat(), getString(R.string.lost))
                )
            }


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