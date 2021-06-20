package com.kani.medzone.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**Created by Guru kathir.J on 02,June,2021 **/
@Entity
data class Report (
    @PrimaryKey(autoGenerate = true) var id:Int,
    @ColumnInfo(name = "name") var name:String,
    @ColumnInfo(name = "date") var takenOn:Long,
    @ColumnInfo(name = "readings") var readingList:List<Reading>
)


// example converter
 class ReadingsConverter {
    @TypeConverter
    fun fromTimestamp(value: String): List<Reading> {
        val type = object : TypeToken<List<Reading>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun readingToString(reading:List<Reading>): String {
        val type = object : TypeToken<List<Reading>>() {}.type
        return Gson().toJson(reading,type)
    }
}