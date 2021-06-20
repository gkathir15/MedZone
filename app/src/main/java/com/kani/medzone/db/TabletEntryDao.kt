package com.kani.medzone.db

import androidx.room.*

/**Created by Guru kathir.J on 29,May,2021 **/
@Dao
interface TabletEntryDao {

//    @Query("SELECT * FROM TabletEntry WHERE date" )
//    suspend fun getAll(startTym:Long,endTym:Long): List<TabletEntry>

    @Query("SELECT * FROM TabletEntry")
    suspend fun getAll(): List<TabletEntry>

//    @Query("SELECT * FROM Tablets WHERE tabletid IN (:tabletid)")
//    fun loadAllByIds(tabletid: IntArray): List<Tablets>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User

    @Insert
    suspend fun insert( tablet: TabletEntry)

    @Delete
    suspend fun delete(tablet: TabletEntry)

    @Update
    suspend  fun update (tablet: TabletEntry)
}