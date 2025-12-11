package com.example.workmateapp.domain.repository

import com.example.workmateapp.domain.model.Country
import kotlinx.coroutines.flow.Flow

interface CountriesRepository {
    fun getCountries(): Flow<List<Country>>
    suspend fun refreshCountries()
    fun getCountryByName(name: String): Flow<Country?>
    suspend fun refreshCountry(name: String)
}

