package com.example.workmateapp.data.repository

import com.example.workmateapp.data.datasource.LocalDataSource
import com.example.workmateapp.data.datasource.RemoteDataSource
import com.example.workmateapp.domain.model.Country
import com.example.workmateapp.domain.repository.CountriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CountriesRepository {
    
    override fun getCountries(): Flow<List<Country>> {
        return localDataSource.getCountries()
            .catch { emit(emptyList()) }
    }
    
    override suspend fun refreshCountries() {
        try {
            val countries = remoteDataSource.getCountries()
            localDataSource.saveCountries(countries)
        } catch (e: Exception) {
            throw e
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
}

