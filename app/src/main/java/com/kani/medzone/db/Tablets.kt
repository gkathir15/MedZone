package com.kani.medzone.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**Created by Guru kathir.J on 02,May,2021 **/
@Entity
data class Tablets(
        @PrimaryKey(autoGenerate = true) val tabletid: Int,
        @ColumnInfo(name = "tabletName") var name: String?,
        @ColumnInfo(name = "mg") var mgDosage: String?,
        @ColumnInfo(name = "availableQty") var available: Int?,
        @ColumnInfo(name = "mealDosage") var mealDosage: Int?,// 1 before food/ 2 after food / 3 with food
        @ColumnInfo(typeAffinity = ColumnInfo.BLOB) var imageUrl: ByteArray?,
        @ColumnInfo(name = "morning") var morning: Int?,//0/1
        @ColumnInfo(name = "noon") var noon: Int?, //0/1,
        @ColumnInfo(name = "night") var night: Int?, //0/1
        @ColumnInfo(name = "evening") var evening: Int?, //0/1
        @ColumnInfo(name = "qty") var qty: Int?,
        @ColumnInfo(name = "instruction") var instruction: String? //0/1
)