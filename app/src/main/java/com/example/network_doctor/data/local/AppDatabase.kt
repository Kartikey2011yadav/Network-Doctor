package com.example.network_doctor.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.network_doctor.data.local.dao.ResultDao
import com.example.network_doctor.data.local.entity.TestResultEntity

@Database(entities = [TestResultEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun resultDao(): ResultDao
}
