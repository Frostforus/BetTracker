package com.frostforus.betTracker.list_data.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.frostforus.betTracker.R
import com.frostforus.betTracker.list_data.BetActivity
import com.frostforus.betTracker.list_data.data.BetItem
import net.glxn.qrgen.android.QRCode

class BetItemDetailsFragment(private val betItem: BetItem) : Fragment() {
    companion object {
        const val TAG = "TAG_FRAGMENT_BET_ITEM_DETAILS"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_item_details, container, false)
        loadBetData(rootView)
        return rootView
    }

    fun loadBetData(rootView: View) {
        //Load bet ID
        (rootView.findViewById<View>(R.id.tv_id) as TextView).text = betItem.id.toString()

        //Load name of who the bet is with
        (rootView.findViewById<View>(R.id.tv_bet_with) as TextView).text =
            betItem.nameOfBetWith.toString()

        //Load bet's description
        (rootView.findViewById<View>(R.id.tv_description) as TextView).text =
            betItem.description.toString()

        //Load bet's category
        (rootView.findViewById<View>(R.id.tv_category) as TextView).text =
            betItem.category.toString()

        //Load bet's pot
        (rootView.findViewById<View>(R.id.tv_pot) as TextView).text = betItem.pot.toString()

        //Load is bet over
        (rootView.findViewById<View>(R.id.tv_bet_over) as TextView).text =
            betItem.status.toString()

        //TODO: Load bet's start date
        (rootView.findViewById<View>(R.id.tv_bet_start) as TextView).text =
            getString(
                R.string.hungarian_date_format,
                betItem.betStartYear,
                betItem.betStartMonth,
                betItem.betStartDay
            )

        //Load bet's end date
        (rootView.findViewById<View>(R.id.tv_bet_end) as TextView).text = getString(
            R.string.hungarian_date_format,
            betItem.betEndYear,
            betItem.betEndMonth,
            betItem.betEndDay
        )

        val newBetItem = betItem
        newBetItem.nameOfBetWith = (activity as BetActivity).user_name
        newBetItem.description = newBetItem.description.replace("\\s".toRegex(), "_")
        val bitmap = QRCode.from(newBetItem.toString()).withSize(1000, 1000).bitmap()

        (rootView.findViewById<View>(R.id.qr_code_image) as ImageView).setImageBitmap(bitmap)

        Log.v("loadingBetItem", betItem.toString())
    }
}