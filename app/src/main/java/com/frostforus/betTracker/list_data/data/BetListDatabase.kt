package com.frostforus.betTracker.list_data.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bme.aut.shoppinglist.data.BetItem


@Database(entities = [BetItem::class], version = 1)
@TypeConverters(value = [BetItem.Category::class])
abstract class BetListDatabase : RoomDatabase() {
    abstract fun betItemDao(): BetItemDao
}