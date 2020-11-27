package com.frostforus.betTracker.list_data.data

import androidx.room.*
import hu.bme.aut.shoppinglist.data.BetItem


@Dao
interface BetItemDao {
    @Query("SELECT * FROM betitem")
    fun getAll(): List<BetItem>

    @Insert
    fun insert(BetItems: BetItem): Long

    @Update
    fun update(BetItem: BetItem)

    @Delete
    fun deleteItem(BetItem: BetItem)
}