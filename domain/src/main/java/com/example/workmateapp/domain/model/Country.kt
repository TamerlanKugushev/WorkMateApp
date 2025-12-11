package com.example.workmateapp.domain.model

data class Country(
    val name: String,
    val flag: String,
    val capital: String?,
    val population: Long,
    val currencies: List<Currency>,
    val region: String?,
    val subregion: String?,
    val languages: List<Language>,
    val maps: String?
)

