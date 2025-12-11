package com.example.workmateapp.core.network.dto

import com.google.gson.annotations.SerializedName

data class CountryDto(
    val name: NameDto,
    val flags: FlagsDto,
    val capital: List<String>?,
    val population: Long,
    val currencies: Map<String, CurrencyDto>?,
    val region: String?,
    val subregion: String?,
    val languages: Map<String, String>?,
    val maps: MapsDto?
)

data class NameDto(
    val common: String,
    val official: String
)

data class FlagsDto(
    val png: String,
    val svg: String
)

data class CurrencyDto(
    val name: String,
    val symbol: String?
)

data class MapsDto(
    @SerializedName("googleMaps")
    val googleMaps: String?
)

