package com.kani.medzone.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**Created by Guru kathir.J on 02,May,2021 **/
@Entity
data class Tablets(
        @PrimaryKey val tabletid: Int,
        @ColumnInfo(name = "tabletName") val name: String?,
        @ColumnInfo(name = "mg") val mgDosage: Int?,
        @ColumnInfo(name = "mealDosage") val mealDosage: Int?,
        @ColumnInfo(name = "imageUrl") val imageUrl: String?,
        @ColumnInfo(name = "dayDosage") val dayDosage: String? //Values will be in form of 1 or 1,2 or 1,3 or 1,2,3 etc
)