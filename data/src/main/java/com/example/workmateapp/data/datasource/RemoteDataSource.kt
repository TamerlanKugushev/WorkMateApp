package com.example.workmateapp.data.datasource

import com.example.workmateapp.core.network.api.CountriesApi
import com.example.workmateapp.core.network.dto.CountryDto
import com.example.workmateapp.data.mapper.CountryMapper
import com.example.workmateapp.domain.model.Country
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: CountriesApi
) {
    suspend fun getCountries(): List<Country> {
        val dtos = api.getAllCountries()
        return dtos.map { CountryMapper.dtoToDomain(it) }
    }
}

