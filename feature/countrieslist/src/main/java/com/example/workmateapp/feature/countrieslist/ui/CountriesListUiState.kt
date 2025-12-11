package com.example.workmateapp.feature.countrieslist.ui

import com.example.workmateapp.common.Result

data class CountriesListUiState(
    val result: Result<List<CountryUi>> = Result.Loading
)

