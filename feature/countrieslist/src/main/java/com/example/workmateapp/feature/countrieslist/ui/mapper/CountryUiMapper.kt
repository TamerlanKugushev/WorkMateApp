package com.example.workmateapp.feature.countrieslist.ui.mapper

import com.example.workmateapp.domain.model.Country
import com.example.workmateapp.feature.countrieslist.ui.CountryUi

object CountryUiMapper {
    fun toUi(country: Country): CountryUi {
        return CountryUi(
            name = country.name,
            flag = country.flag,
            capital = country.capital,
            population = country.population,
            region = country.region
        )
    }
    
    fun toUiList(countries: List<Country>): List<CountryUi> {
        return countries.map { toUi(it) }
    }
}

