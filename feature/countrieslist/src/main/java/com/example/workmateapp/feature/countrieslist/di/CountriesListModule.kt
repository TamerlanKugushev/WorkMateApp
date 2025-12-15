package com.example.workmateapp.feature.countrieslist.di

import com.example.workmateapp.domain.usecase.GetCountriesListUseCase
import com.example.workmateapp.feature.countrieslist.ui.CountriesListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class CountriesListModule {
    
    @Provides
    fun provideCountriesListViewModelFactory(
        getCountriesListUseCase: GetCountriesListUseCase
    ): CountriesListViewModelFactory {
        return CountriesListViewModelFactory(getCountriesListUseCase)
    }
}

