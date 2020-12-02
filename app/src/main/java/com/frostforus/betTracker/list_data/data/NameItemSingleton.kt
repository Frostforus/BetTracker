package com.frostforus.betTracker.list_data.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nameitemsingleton")
data class NameItemSingleton(
    @ColumnInfo(name = "id") @PrimaryKey val id: Long? = 1,
    @ColumnInfo(name = "name") val name: String
)