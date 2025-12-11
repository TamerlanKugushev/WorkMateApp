package com.example.workmateapp.feature.countrieslist.di

import com.example.workmateapp.domain.usecase.GetCountriesListUseCase
import com.example.workmateapp.feature.countrieslist.ui.CountriesListViewModel
import dagger.Module
import dagger.Provides

@Module
class CountriesListModule {
    
    @Provides
    fun provideCountriesListViewModel(
        getCountriesListUseCase: GetCountriesListUseCase
    ): CountriesListViewModel {
        return CountriesListViewModel(getCountriesListUseCase)
    }
}

