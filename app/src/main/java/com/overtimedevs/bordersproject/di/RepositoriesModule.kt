package com.overtimedevs.bordersproject.di

import android.content.Context
import com.overtimedevs.bordersproject.data.data_source.local.CountryLocalDataSource
import com.overtimedevs.bordersproject.data.data_source.remote.CountryRemoteDataSource
import com.overtimedevs.bordersproject.data.repository.CountryRepository
import com.overtimedevs.bordersproject.data.repository.SessionRepository
import com.overtimedevs.bordersproject.data.repository.UserRepository
import com.overtimedevs.bordersproject.data.util.NetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {

    @Singleton
    @Provides
    fun providesCountryRepository(
        netManager: NetManager,
        sessionRepository: SessionRepository,
        localDataSource: CountryLocalDataSource,
        remoteDataSource: CountryRemoteDataSource
    ): CountryRepository =
        CountryRepository(
            localDataSource = localDataSource,
            netManager = netManager,
            remoteDataSource = remoteDataSource,
            sessionRepository = sessionRepository
        )

    @Singleton
    @Provides
    fun providesSessionRepository(@ApplicationContext context: Context) : SessionRepository =
        SessionRepository(context)

    @Singleton
    @Provides
    fun providesUserRepository(@ApplicationContext context: Context) : UserRepository =
        UserRepository(context)

}