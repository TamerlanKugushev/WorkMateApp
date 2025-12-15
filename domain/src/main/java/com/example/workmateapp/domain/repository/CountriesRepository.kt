package com.example.workmateapp.domain.repository

import androidx.paging.PagingData
import com.example.workmateapp.domain.model.Country
import kotlinx.coroutines.flow.Flow

interface CountriesRepository {
    fun getCountries(): Flow<List<Country>>
    fun getCountriesPaged(): Flow<PagingData<Country>>
    suspend fun refreshCountries()
    suspend fun refreshCountriesIfStale(): Boolean
    fun getCountryByName(name: String): Flow<Country?>
    suspend fun refreshCountry(name: String)
    suspend fun hasCache(): Boolean
}

