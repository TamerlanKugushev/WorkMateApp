package com.example.workmateapp.feature.countrydetails.ui

import com.example.workmateapp.common.Result

data class CountryDetailsUiState(
    val result: Result<CountryDetailsUi> = Result.Loading
)

