package com.example.workmateapp.data.datasource

import com.example.workmateapp.core.network.api.CountriesApi
import com.example.workmateapp.core.network.dto.CountryDto
import com.example.workmateapp.data.mapper.CountryMapper
import com.example.workmateapp.domain.model.Country
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: CountriesApi
) {
    suspend fun getCountries(): List<Country> {
        try {
            val regions = listOf("Africa", "Americas", "Asia", "Europe", "Oceania")
            
            val allCountries = coroutineScope {
                regions.map { region ->
                    async {
                        try {
                            val dtos = api.getCountriesByRegion(region)
                            
                            dtos.mapNotNull { dto ->
                                try {
                                    CountryMapper.dtoToDomain(dto)
                                } catch (e: Exception) {
                                    null
                                }
                            }
                        } catch (e: Exception) {
                            emptyList()
                        }
                    }
                }.map { it.await() }
                    .flatten()
            }
            
            return allCountries
        } catch (e: Exception) {
            throw e
        }
    }
}

