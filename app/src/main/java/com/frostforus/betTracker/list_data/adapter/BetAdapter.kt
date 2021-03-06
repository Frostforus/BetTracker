package com.frostforus.betTracker.list_data.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.frostforus.betTracker.R
import com.frostforus.betTracker.list_data.BetActivity
import com.frostforus.betTracker.list_data.data.BetItem
import com.frostforus.betTracker.list_data.fragments.BetItemDetailsFragment
import kotlin.concurrent.thread

class BetAdapter(private val listener: BetItemClickListener, private val activity: BetActivity) :
    RecyclerView.Adapter<BetAdapter.BetViewHolder>() {


    private val items = mutableListOf<BetItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BetViewHolder {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_bet_list, parent, false)
        Log.v("creatingbetadapter", items.toString())
        return BetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BetViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.nameOfBetWith
        holder.descriptionTextView.text = item.description
        holder.categoryTextView.text = item.category.name
        holder.priceTextView.text = item.pot
        holder.iconImageView.setImageResource(getImageResource(item.category))
        holder.isBoughtCheckBox.isChecked = (item.status.name == BetItem.Status.WON.name)

        holder.item = item
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: BetItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    //hihi
    fun deleteItem(item: BetItem) {
        items.remove(item)
        notifyItemRemoved(items.indexOf(item))
    }

    fun update(shoppingItems: List<BetItem>) {
        items.clear()
        items.addAll(shoppingItems)
        notifyDataSetChanged()
    }

    interface BetItemClickListener {
        fun onItemChanged(item: BetItem)
        fun onItemDeleted(item: BetItem)
    }

    @DrawableRes
    private fun getImageResource(category: BetItem.Category) = when (category) {
        BetItem.Category.ITEM -> R.drawable.light_bulb
        BetItem.Category.ACTION -> R.drawable.mega_phone
        BetItem.Category.MONEY -> R.drawable.cash
    }

    inner class BetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val iconImageView: ImageView
        val nameTextView: TextView
        val descriptionTextView: TextView
        val categoryTextView: TextView
        val priceTextView: TextView
        val isBoughtCheckBox: CheckBox
        val removeButton: ImageButton
        val viewButton: Button

        var item: BetItem? = null


        init {
            iconImageView = itemView.findViewById(R.id.BetItemIconImageView)
            nameTextView = itemView.findViewById(R.id.BetItemNameTextView)
            descriptionTextView = itemView.findViewById(R.id.BetItemDescriptionTextView)
            categoryTextView = itemView.findViewById(R.id.BetItemCategoryTextView)
            priceTextView = itemView.findViewById(R.id.BetItemPriceTextView)
            isBoughtCheckBox = itemView.findViewById(R.id.BetItemIsWonCheckBox)
            removeButton = itemView.findViewById(R.id.BetItemRemoveButton)
            viewButton = itemView.findViewById(R.id.btn_view_single_bet)
            //hihi
            removeButton.setOnClickListener {
                item?.let {
                    listener.onItemDeleted(it)
                    Log.v("Listener", "Calling delete")
                }
            }

            viewButton.setOnClickListener {
                thread {
                    activity.showFragmentByTag(
                        BetItemDetailsFragment.TAG, activity.database.betItemDao().getById(
                            item?.id
                        )
                    )
                    Log.v("Listener", "Its working")
                }

            }


        }
    }


}