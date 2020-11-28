package com.frostforus.betTracker.list_data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.frostforus.betTracker.R
import hu.bme.aut.shoppinglist.data.BetItem

class BetAdapter(private val listener: BetItemClickListener) :
    RecyclerView.Adapter<BetAdapter.BetViewHolder>() {


    private val items = mutableListOf<BetItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BetViewHolder {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_bet_list, parent, false)
        return BetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BetViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.nameOfBetWith
        holder.descriptionTextView.text = item.description
        holder.categoryTextView.text = item.category.name
        holder.priceTextView.text = item.pot + " Ft"
        holder.iconImageView.setImageResource(getImageResource(item.category))
        holder.isBoughtCheckBox.isChecked = item.betOver

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
        BetItem.Category.ITEM -> R.drawable.hand_shake
        BetItem.Category.ACTION -> R.drawable.hand_shake
        BetItem.Category.MONEY -> R.drawable.hand_shake
    }

    inner class BetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView: ImageView
        val nameTextView: TextView
        val descriptionTextView: TextView
        val categoryTextView: TextView
        val priceTextView: TextView
        val isBoughtCheckBox: CheckBox
        val removeButton: ImageButton

        var item: BetItem? = null


        init {
            iconImageView = itemView.findViewById(R.id.ShoppingItemIconImageView)
            nameTextView = itemView.findViewById(R.id.ShoppingItemNameTextView)
            descriptionTextView = itemView.findViewById(R.id.ShoppingItemDescriptionTextView)
            categoryTextView = itemView.findViewById(R.id.ShoppingItemCategoryTextView)
            priceTextView = itemView.findViewById(R.id.ShoppingItemPriceTextView)
            isBoughtCheckBox = itemView.findViewById(R.id.ShoppingItemIsBoughtCheckBox)
            removeButton = itemView.findViewById(R.id.ShoppingItemRemoveButton)
            //hihi
            removeButton.setOnClickListener {
                item?.let {
                    listener.onItemDeleted(it)
                }
            }

        }
    }


}