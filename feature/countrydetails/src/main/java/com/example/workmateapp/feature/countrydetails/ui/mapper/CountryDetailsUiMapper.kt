package com.example.workmateapp.feature.countrydetails.ui.mapper

import com.example.workmateapp.domain.model.Country
import com.example.workmateapp.feature.countrydetails.ui.CountryDetailsUi
import com.example.workmateapp.feature.countrydetails.ui.CurrencyUi
import com.example.workmateapp.feature.countrydetails.ui.LanguageUi

object CountryDetailsUiMapper {
    fun toUi(country: Country?): CountryDetailsUi? {
        return country?.let {
            CountryDetailsUi(
                name = it.name,
                flag = it.flag,
                capital = it.capital,
                population = it.population,
                currencies = it.currencies.map { currency ->
                    CurrencyUi(
                        code = currency.code,
                        name = currency.name,
                        symbol = currency.symbol
                    )
                },
                region = it.region,
                subregion = it.subregion,
                languages = it.languages.map { language ->
                    LanguageUi(
                        code = language.code,
                        name = language.name
                    )
                },
                maps = it.maps
            )
        }
    }
}

