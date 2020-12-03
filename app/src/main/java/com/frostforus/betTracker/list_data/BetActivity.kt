package com.frostforus.betTracker.list_data

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.frostforus.betTracker.R
import com.frostforus.betTracker.list_data.adapter.BetAdapter
import com.frostforus.betTracker.list_data.data.BetItem
import com.frostforus.betTracker.list_data.data.BetListDatabase
import com.frostforus.betTracker.list_data.fragments.BetItemDetailsFragment
import com.frostforus.betTracker.list_data.fragments.NewBetItemDialogFragment
import com.frostforus.betTracker.list_data.fragments.RecyclerViewFragment
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.integration.android.IntentIntegrator
import java.lang.Enum
import kotlin.concurrent.thread

class BetActivity : AppCompatActivity(),
    NewBetItemDialogFragment.NewBetItemDialogListener {

    lateinit var database: BetListDatabase

    lateinit var adapter: BetAdapter

    lateinit var userName: String

    var FIRST_FLAG: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bets)
        database = Room.databaseBuilder(
            applicationContext,
            BetListDatabase::class.java,
            "bet-list"
        ).fallbackToDestructiveMigration().build()
        loadName()

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
            Log.v("creat", newItem.toString())
            val newId = database.betItemDao().insert(newItem)
            val newShoppingItem = newItem.copy(
                id = newId
            )
            //delete runonui thread coz were on the main thread
            adapter.addItem(newShoppingItem)
        }
    }

    fun loadName() {
        thread {
            userName = if (database.NameItemSingletonDao().getName().isNullOrEmpty()) {
                "default_user_name"
            } else {
                database.NameItemSingletonDao().getName()[0].name
            }
        }
    }

    // Get the results:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) Log.v("qrcode", "Cancelled")
            else parseQRResult(result.contents)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
            Log.v("qrcode", "NO RESULT...")
        }
    }

    private fun parseQRResult(QRResult: String) {
        Log.v("qrcode", "QRCODE FOUND: $QRResult")
        val attributes = QRResult.split(" ").drop(1)

        for (i in attributes) {
            Log.v("qrcode", i.dropLast(1).split("=")[1])
        }
        try {
            onBetItemCreated(
                BetItem(
                    null,
                    attributes[0].dropLast(1).split("=")[1].replace("_", " "),
                    attributes[1].dropLast(1).split("=")[1].replace("_", " "),
                    stringToCategory(attributes[2].dropLast(1).split("=")[1]),
                    attributes[3].dropLast(1).split("=")[1].replace("_", " "),
                    stringToStatus(attributes[4].dropLast(1).split("=")[1]),
                    attributes[5].dropLast(1).split("=")[1].toShort(),
                    attributes[6].dropLast(1).split("=")[1].toShort(),
                    attributes[7].dropLast(1).split("=")[1].toShort(),
                    attributes[8].dropLast(1).split("=")[1].toShort(),
                    attributes[9].dropLast(1).split("=")[1].toShort(),
                    attributes[10].dropLast(1).split("=")[1].toShort()
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Log.v("qrcode", "Bad QRCODE")
            Snackbar.make(
                findViewById(R.id.activity_bets),
                "Error in reading QRCODE",
                Snackbar.LENGTH_LONG
            ).show()

        }

    }


    private fun stringToCategory(x: String): BetItem.Category {
        return Enum.valueOf<BetItem.Category>(BetItem.Category::class.java, x)
    }

    private fun stringToStatus(x: String): BetItem.Status {
        return Enum.valueOf<BetItem.Status>(BetItem.Status::class.java, x)
    }
}