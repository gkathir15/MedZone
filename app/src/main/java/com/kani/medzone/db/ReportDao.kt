package com.kani.medzone.db

import androidx.room.*

/**Created by Guru kathir.J on 05,June,2021 **/
@Dao
interface ReportDao {

        @Query("SELECT * FROM Report")
        suspend fun getAll(): List<Report>

//    @Query("SELECT * FROM Tablets WHERE tabletid IN (:tabletid)")
//    fun loadAllByIds(tabletid: IntArray): List<Tablets>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User

        @Insert
        suspend fun insert( report: Report)

        @Delete
        suspend fun delete(report: Report)

        @Update
        suspend  fun update (report: Report)

}