package com.example.network_doctor.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_results")
data class TestResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,
    val downloadSpeed: Float,
    val uploadSpeed: Float,
    val ping: Float,
    val jitter: Float,
    val loss: Float,
    val networkType: String
)
