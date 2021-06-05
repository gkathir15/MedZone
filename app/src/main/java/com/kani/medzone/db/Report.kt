package com.kani.medzone.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson

/**Created by Guru kathir.J on 02,June,2021 **/
@Entity
data class Report (
    @PrimaryKey(autoGenerate = true) var id:Int,
    @ColumnInfo(name = "name") var name:String,
    @ColumnInfo(name = "date") var takenOn:Long,
    @ColumnInfo(name = "readings") var readingList:ArrayList<Reading>
)


// example converter
 class ReadingsConverter {
    @TypeConverter
    fun fromTimestamp(value: String): ArrayList<Reading> {
        return Gson().fromJson(value, ArrayList<Reading>().javaClass)
    }

    @TypeConverter
    fun readingToString(reading:ArrayList<Reading>): String {
        return Gson().toJson(reading)
    }
}