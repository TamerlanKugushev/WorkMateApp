package com.example.workmateapp.core.network.api

import com.example.workmateapp.core.network.dto.CountryDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CountriesApi {
    @GET("v3.1/all")
    suspend fun getAllCountries(
        @Query("fields") fields: String = "name,flags,capital,population,currencies,region,subregion,languages,maps"
    ): List<CountryDto>
}

