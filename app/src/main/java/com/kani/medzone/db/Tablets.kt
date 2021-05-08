package com.kani.medzone.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**Created by Guru kathir.J on 02,May,2021 **/
@Entity
data class Tablets(
        @PrimaryKey(autoGenerate = true) val tabletid: Int,
        @ColumnInfo(name = "tabletName") var name: String?,
        @ColumnInfo(name = "mg") var mgDosage: Int?,
        @ColumnInfo(name = "mealDosage") var mealDosage: Int?,// 1 before food/ 2 after food / 3 with food
        @ColumnInfo(name = "imageUrl") var imageUrl: String?,
        @ColumnInfo(name = "breakfast") var breakfast: Int? ,//0/1
        @ColumnInfo(name = "lunch") var lunch: Int?, //0/1,
        @ColumnInfo(name = "dinner") var dinner: Int? //0/1
)