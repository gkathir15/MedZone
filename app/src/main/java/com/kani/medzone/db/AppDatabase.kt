package com.kani.medzone.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**Created by Guru kathir.J on 02,May,2021 **/
@Database(entities = [Tablets::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tabletsDao(): TabletsDao
}