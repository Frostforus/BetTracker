package com.frostforus.betTracker.list_data.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.frostforus.betTracker.R
import com.frostforus.betTracker.list_data.BetActivity
import com.frostforus.betTracker.list_data.adapter.BetAdapter
import com.frostforus.betTracker.list_data.data.BetItem
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureActivity
import kotlin.concurrent.thread


class RecyclerViewFragment : Fragment(), BetAdapter.BetItemClickListener {
    companion object {
        const val TAG = "TAG_FRAGMENT_RECYCLER_VIEW"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BetAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false)
        loadItemsInBackground()
        adapter = BetAdapter(this, (activity as BetActivity))
        (activity as BetActivity).adapter = adapter

        recyclerView = rootView.findViewById<View>(R.id.MainRecyclerView) as RecyclerView
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.layoutManager = LinearLayoutManager((activity as BetActivity))
        recyclerView.adapter = adapter


        val fab = rootView.findViewById<View>(R.id.fab)
        fab.setOnClickListener {
            NewBetItemDialogFragment().show(
                (activity as BetActivity).supportFragmentManager,
                NewBetItemDialogFragment.TAG
            )
            Log.v("clicked fab", "hello")
        }

        val fab2 = rootView.findViewById<View>(R.id.fab2)
        fab2.setOnClickListener {
            scanQRCode()
            Log.v("qrcode", "Scanning for QRCODE")
        }

        return rootView
    }

    private fun loadItemsInBackground() {
        thread {

            val items = (activity as BetActivity).database.betItemDao().getAll()
            //delete run on ui thread coz were on the main thread
            adapter.update(items)

        }
    }


    override fun onItemChanged(item: BetItem) {
        thread {
            (activity as BetActivity).database.betItemDao().update(item)
            Log.d("MainActivity", "ShoppingItem update was successful")
        }
    }

    //hihi
    override fun onItemDeleted(item: BetItem) {
        thread {
            (activity as BetActivity).database.betItemDao().deleteItem(item)
        }
        //delete runonui thread coz were on the main thread
        adapter.deleteItem(item)

    }

    // QR CODE SCANNER
    private fun scanQRCode() {
        val integrator = IntentIntegrator((activity as BetActivity)).apply {
            captureActivity = CaptureActivity::class.java
            setOrientationLocked(false)
            setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
            setPrompt("Scanning Code")
        }
        integrator.initiateScan()
    }


}