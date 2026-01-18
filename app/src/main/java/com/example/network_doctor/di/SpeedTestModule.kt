package com.example.network_doctor.di

import com.example.network_doctor.data.repository.SpeedTestRepositoryImpl
import com.example.network_doctor.domain.repository.SpeedTestRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SpeedTestModule {

    @Binds
    @Singleton
    abstract fun bindSpeedTestRepository(
        speedTestRepositoryImpl: SpeedTestRepositoryImpl
    ): SpeedTestRepository
}
