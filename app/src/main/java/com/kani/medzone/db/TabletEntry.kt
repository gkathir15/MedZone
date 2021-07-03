package com.kani.medzone.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

/**Created by Guru kathir.J on 22,May,2021 **/
@Entity
data class TabletEntry (
    @PrimaryKey val id: String,
    @ColumnInfo(name = "date") var date: Long?,
    @ColumnInfo(name = "status") var status:Int =0,//1,2,3 as taken,snoozed,skipped
    @Embedded var tablet:Tablets,
)