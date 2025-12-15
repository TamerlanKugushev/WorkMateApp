package com.example.workmateapp.feature.countrydetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workmateapp.domain.usecase.GetCountryDetailsUseCase
import javax.inject.Inject

class CountryDetailsViewModelFactory @Inject constructor(
    private val getCountryDetailsUseCase: GetCountryDetailsUseCase
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryDetailsViewModel::class.java)) {
            return CountryDetailsViewModel(getCountryDetailsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
