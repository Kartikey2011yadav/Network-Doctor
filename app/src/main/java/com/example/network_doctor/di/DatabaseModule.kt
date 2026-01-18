package com.example.network_doctor.di

import android.content.Context
import androidx.room.Room
import com.example.network_doctor.data.local.AppDatabase
import com.example.network_doctor.data.local.dao.ResultDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "network_doctor_db"
        ).build()
    }

    @Provides
    fun provideResultDao(database: AppDatabase): ResultDao {
        return database.resultDao()
    }
}
