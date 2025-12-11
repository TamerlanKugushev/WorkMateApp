package com.example.workmateapp.feature.countrydetails.ui

data class CountryDetailsUi(
    val name: String,
    val flag: String,
    val capital: String?,
    val population: Long,
    val currencies: List<CurrencyUi>,
    val region: String?,
    val subregion: String?,
    val languages: List<LanguageUi>,
    val maps: String?
)

data class CurrencyUi(
    val code: String,
    val name: String,
    val symbol: String?
)

data class LanguageUi(
    val code: String,
    val name: String
)

