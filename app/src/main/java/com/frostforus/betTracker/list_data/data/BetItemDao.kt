package com.frostforus.betTracker.list_data.data

import androidx.room.*


@Dao
interface BetItemDao {
    @Query("SELECT * FROM betitem")
    fun getAll(): List<BetItem>

    @Query("SELECT COUNT(id) FROM betitem WHERE status = 3")
    fun getBetsLost(): Int

    @Query("SELECT COUNT(id) FROM betitem WHERE status = 1")
    fun getBetsWon(): Int

    @Query("SELECT COUNT(id) FROM betitem WHERE status = 0")
    fun getBetsInProgress(): Int

    @Query("SELECT * FROM betitem WHERE id = (:betId)")
    fun getById(betId: Long?): BetItem?

    @Insert
    fun insert(BetItems: BetItem): Long

    @Update
    fun update(BetItem: BetItem)

    @Delete
    fun deleteItem(BetItem: BetItem)
}