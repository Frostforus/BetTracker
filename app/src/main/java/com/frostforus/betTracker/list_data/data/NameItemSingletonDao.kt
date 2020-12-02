package com.frostforus.betTracker.list_data.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NameItemSingletonDao {
    @Query("SELECT * FROM nameitemsingleton")
    fun getName(): List<NameItemSingleton>

    @Insert
    fun insert(nameItemSingleton: NameItemSingleton): Long

    @Update
    fun update(nameItemSingleton: NameItemSingleton)


}