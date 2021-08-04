package com.kani.medzone.db

import androidx.room.*
import com.kani.medzone.vm.EventEntry

/**Created by Guru kathir.J on 25,July,2021 **/
@Dao
interface EventDao {
    @Query("SELECT * FROM EventEntry")
    suspend fun getAll(): List<EventEntry>


    @Insert
    suspend fun insert(report: EventEntry)

    @Delete
    suspend fun delete(report: EventEntry)

    @Update
    suspend  fun update (report: EventEntry)
}