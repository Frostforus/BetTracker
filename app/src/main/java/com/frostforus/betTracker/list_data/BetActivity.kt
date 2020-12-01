package com.frostforus.betTracker.list_data

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.frostforus.betTracker.R
import com.frostforus.betTracker.list_data.adapter.BetAdapter
import com.frostforus.betTracker.list_data.data.BetListDatabase
import com.frostforus.betTracker.list_data.fragments.BetItemDetailsFragment
import com.frostforus.betTracker.list_data.fragments.NewBetItemDialogFragment
import com.frostforus.betTracker.list_data.fragments.RecyclerViewFragment
import hu.bme.aut.shoppinglist.data.BetItem
import kotlin.concurrent.thread

class BetActivity : AppCompatActivity(),
    NewBetItemDialogFragment.NewBetItemDialogListener {

    lateinit var database: BetListDatabase

    lateinit var adapter: BetAdapter

    var FIRST_FLAG: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bets)

        database = Room.databaseBuilder(
            applicationContext,
            BetListDatabase::class.java,
            "bet-list"
        ).fallbackToDestructiveMigration().build()

        showFragmentByTag(RecyclerViewFragment.TAG)
    }

    fun showFragmentByTag(tag: String, betItem: BetItem? = null) {
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            if (RecyclerViewFragment.TAG == tag) {
                fragment = RecyclerViewFragment()
            } else if (BetItemDetailsFragment.TAG == tag && betItem != null) {
                fragment = BetItemDetailsFragment(betItem)
            }
        }

        if (fragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragmentContainer, fragment, tag)

            if (!FIRST_FLAG) {
                ft.addToBackStack(null) // add fragment transaction to the back stack
                Log.v("BackStack", "Added to backstack")
            } else {
                FIRST_FLAG = !FIRST_FLAG
            }
            ft.commit()
        }
    }

    override fun onBetItemCreated(newItem: BetItem) {
        thread {
            val newId = database.betItemDao().insert(newItem)
            val newShoppingItem = newItem.copy(
                id = newId
            )
            //delete runonui thread coz were on the main thread
            adapter.addItem(newShoppingItem)
        }
    }

}