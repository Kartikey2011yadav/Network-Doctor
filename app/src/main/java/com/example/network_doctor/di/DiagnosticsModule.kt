package com.example.network_doctor.di

import com.example.network_doctor.data.repository.DiagnosticsRepositoryImpl
import com.example.network_doctor.domain.repository.DiagnosticsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DiagnosticsModule {

    @Binds
    @Singleton
    abstract fun bindDiagnosticsRepository(
        diagnosticsRepositoryImpl: DiagnosticsRepositoryImpl
    ): DiagnosticsRepository
}
