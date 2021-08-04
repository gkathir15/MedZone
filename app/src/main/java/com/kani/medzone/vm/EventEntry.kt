package com.kani.medzone.vm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**Created by Guru kathir.J on 25,July,2021 **/
@Entity
data class EventEntry(
    @PrimaryKey(autoGenerate = true)val id:Int,
    @ColumnInfo(name = "time")var time:Long,
    @ColumnInfo(name = "detail")var detail:String,
    @ColumnInfo(name = "isNotify")var isNotify:Boolean
)
