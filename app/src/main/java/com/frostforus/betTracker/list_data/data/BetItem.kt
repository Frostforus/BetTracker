package hu.bme.aut.shoppinglist.data

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

//TODO: have won/lost/in progress there needs to be another enum which tracks what the status is: In progress/won/lost
@Entity(tableName = "betitem")
data class BetItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "name_of_bet_with") val nameOfBetWith: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "category") val category: Category,
    @ColumnInfo(name = "pot") val pot: String,
    @ColumnInfo(name = "bet_over") val betOver: Boolean,

    @ColumnInfo(name = "bet_end_year") val betEndYear: Short,
    @ColumnInfo(name = "bet_end_month") val betEndMonth: Short,
    @ColumnInfo(name = "bet_end_day") val betEndDay: Short,
    @ColumnInfo(name = "bet_start_year") val betStartYear: Short,
    @ColumnInfo(name = "bet_start_month") val betStartMonth: Short,
    @ColumnInfo(name = "bet_start_day") val betStartDay: Short
) {
    init {
        Log.v("BetItem created", this.toString())
    }

    enum class Category {
        MONEY, ACTION, ITEM;

        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Category? {
                return values().find { it.ordinal == ordinal }
            }

            @JvmStatic
            @TypeConverter
            fun toInt(category: Category): Int {
                return category.ordinal
            }
        }
    }
}