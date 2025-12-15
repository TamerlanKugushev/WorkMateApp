package com.example.workmateapp.core.network.api

import com.example.workmateapp.core.network.dto.CountryDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface CountriesApi {
    @GET("v3.1/all")
    suspend fun getAllCountries(
        @Query("fields") fields: String = "name,flags,capital,population,region,subregion"
    ): List<CountryDto>
    
    @GET("v3.1/region/{region}")
    suspend fun getCountriesByRegion(
        @Path("region") region: String,
        @Query("fields") fields: String = "name,flags,capital,population,region,subregion"
    ): List<CountryDto>
}

