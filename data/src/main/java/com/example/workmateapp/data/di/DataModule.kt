package com.example.workmateapp.data.di

import android.content.Context
import com.example.workmateapp.data.cache.CacheManager
import com.example.workmateapp.data.datasource.LocalDataSource
import com.example.workmateapp.data.datasource.RemoteDataSource
import com.example.workmateapp.data.repository.CountriesRepositoryImpl
import com.example.workmateapp.domain.repository.CountriesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule(
    private val context: Context,
    private val networkComponent: com.example.workmateapp.core.network.di.NetworkComponent,
    private val databaseComponent: com.example.workmateapp.core.database.di.DatabaseComponent
) {
    
    @Provides
    @Singleton
    fun provideContext(): Context = context
    
    @Provides
    @Singleton
    fun provideCacheManager(context: Context): CacheManager {
        return CacheManager(context)
    }
    
    @Provides
    @Singleton
    fun provideRemoteDataSource(): RemoteDataSource {
        return RemoteDataSource(networkComponent.countriesApi())
    }
    
    @Provides
    @Singleton
    fun provideLocalDataSource(): LocalDataSource {
        return LocalDataSource(databaseComponent.countryDao())
    }
    
    @Provides
    @Singleton
    fun provideCountriesRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        cacheManager: CacheManager
    ): CountriesRepository {
        return CountriesRepositoryImpl(remoteDataSource, localDataSource, cacheManager)
    }
}

