package com.example.workmateapp.feature.countrydetails.di

import com.example.workmateapp.domain.usecase.GetCountryDetailsUseCase
import com.example.workmateapp.feature.countrydetails.ui.CountryDetailsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class CountryDetailsModule {
    
    @Provides
    fun provideCountryDetailsViewModelFactory(
        getCountryDetailsUseCase: GetCountryDetailsUseCase
    ): CountryDetailsViewModelFactory {
        return CountryDetailsViewModelFactory(getCountryDetailsUseCase)
    }
}

