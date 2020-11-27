package com.frostforus.betTracker.list_data

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.frostforus.betTracker.R
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_stats.*


class StatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        //TODO: clean logcat
        Log.i("Event_info", "StatsActivity loaded")

        loadStats()
    }

    private fun loadStats() {
        val entries = listOf(
            //TODO: get this shit to work
            //PieEntry(DataManager.holidays.toFloat(), "Taken"),
            //PieEntry(DataManager.remainingHolidays.toFloat(), "Remaining"),
            PieEntry(5.toFloat(), "Vagine"),
            PieEntry(2.toFloat(), "XDDDDD"),
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