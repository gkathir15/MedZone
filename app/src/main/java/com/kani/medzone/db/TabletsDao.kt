package com.kani.medzone.db

import androidx.room.*
import com.google.common.collect.Table

/**Created by Guru kathir.J on 02,May,2021 **/
@Dao
interface TabletsDao {
    @Query("SELECT * FROM Tablets")
    fun getAll(): List<Tablets>

//    @Query("SELECT * FROM Tablets WHERE tabletid IN (:tabletid)")
//    fun loadAllByIds(tabletid: IntArray): List<Tablets>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User

    @Insert
    fun insert( tablet: Tablets)

    @Delete
    fun delete(tablet: Tablets)

    @Update
    fun update (tablet: Tablets)
}