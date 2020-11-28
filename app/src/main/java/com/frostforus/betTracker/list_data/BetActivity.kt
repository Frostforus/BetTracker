package com.frostforus.betTracker.list_data

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.frostforus.betTracker.R
import com.frostforus.betTracker.list_data.adapter.BetAdapter
import com.frostforus.betTracker.list_data.data.BetListDatabase
import com.frostforus.betTracker.list_data.fragments.NewBetItemDialogFragment
import hu.bme.aut.shoppinglist.data.BetItem
import kotlinx.android.synthetic.main.activity_bets.*
import kotlinx.android.synthetic.main.content_main.*
import kotlin.concurrent.thread

class BetActivity : AppCompatActivity(), BetAdapter.BetItemClickListener,
    NewBetItemDialogFragment.NewBetItemDialogListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BetAdapter
    private lateinit var database: BetListDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bets)


        fab.setOnClickListener {
            NewBetItemDialogFragment().show(
                supportFragmentManager,
                NewBetItemDialogFragment.TAG
            )
        }
        database = Room.databaseBuilder(
            applicationContext,
            BetListDatabase::class.java,
            "bet-list"
        ).fallbackToDestructiveMigration().build()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView = MainRecyclerView
        adapter = BetAdapter(this)
        loadItemsInBackground()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.betItemDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }


    override fun onItemChanged(item: BetItem) {
        thread {
            database.betItemDao().update(item)
            Log.d("MainActivity", "ShoppingItem update was successful")
        }
    }

    //hihi
    override fun onItemDeleted(item: BetItem) {
        thread {
            database.betItemDao().deleteItem(item)
        }
        runOnUiThread {
            adapter.deleteItem(item)
        }
    }

    override fun onBetItemCreated(newItem: BetItem) {
        thread {
            val newId = database.betItemDao().insert(newItem)
            val newShoppingItem = newItem.copy(
                id = newId
            )
            runOnUiThread {
                adapter.addItem(newShoppingItem)
            }
        }
    }
}