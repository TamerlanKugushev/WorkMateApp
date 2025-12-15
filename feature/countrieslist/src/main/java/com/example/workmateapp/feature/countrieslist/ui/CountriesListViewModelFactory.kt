package com.example.workmateapp.feature.countrieslist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workmateapp.domain.usecase.GetCountriesListUseCase
import javax.inject.Inject

class CountriesListViewModelFactory @Inject constructor(
    private val getCountriesListUseCase: GetCountriesListUseCase
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountriesListViewModel::class.java)) {
            return CountriesListViewModel(getCountriesListUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
