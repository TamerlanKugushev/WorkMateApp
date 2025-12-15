package com.example.workmateapp.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.workmateapp.data.cache.CacheManager
import com.example.workmateapp.data.datasource.LocalDataSource
import com.example.workmateapp.data.datasource.RemoteDataSource
import com.example.workmateapp.data.paging.CountriesPagingSource
import com.example.workmateapp.domain.model.Country
import com.example.workmateapp.domain.repository.CountriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val cacheManager: CacheManager
) : CountriesRepository {
    
    companion object {
        private const val TAG = "CountriesRepository"
        private const val PAGE_SIZE = 20
    }
    
    override fun getCountries(): Flow<List<Country>> {
        return localDataSource.getCountries()
            .catch { emit(emptyList()) }
    }
    
    override fun getCountriesPaged(): Flow<PagingData<Country>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = { CountriesPagingSource(localDataSource) }
        ).flow
    }
    
    override suspend fun refreshCountries() {
        try {
            Log.d(TAG, "Starting refreshCountries")
            val countries = remoteDataSource.getCountries()
            Log.d(TAG, "Got ${countries.size} countries from remote")
            localDataSource.saveCountries(countries)
            cacheManager.updateCacheTimestamp()
            Log.d(TAG, "Successfully refreshed countries")
        } catch (e: Exception) {
            Log.e(TAG, "Error refreshing countries", e)
            throw e
        }
    }
    
    override suspend fun refreshCountriesIfStale(): Boolean {
        return if (cacheManager.isCacheStale()) {
            try {
                Log.d(TAG, "Cache is stale, refreshing...")
                refreshCountries()
                true
            } catch (e: Exception) {
                Log.e(TAG, "Error refreshing stale cache", e)
                false
            }
        } else {
            Log.d(TAG, "Cache is still valid, skipping refresh")
            false
        }
    }
    
    override fun getCountryByName(name: String): Flow<Country?> {
        return localDataSource.getCountryByName(name)
            .catch { emit(null) }
    }
    
    override suspend fun refreshCountry(name: String) {
        try {
            val allCountries = remoteDataSource.getCountries()
            val country = allCountries.find { it.name == name }
            country?.let {
                localDataSource.saveCountry(it)
            }
        } catch (e: Exception) {
            throw e
        }
    }
    
    override suspend fun hasCache(): Boolean {
        return cacheManager.hasCache() && localDataSource.getCountriesCount() > 0
    }
}
