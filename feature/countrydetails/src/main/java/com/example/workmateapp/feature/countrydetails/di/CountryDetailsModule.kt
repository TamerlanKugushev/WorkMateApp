package com.example.workmateapp.feature.countrydetails.di

import com.example.workmateapp.domain.usecase.GetCountryDetailsUseCase
import com.example.workmateapp.feature.countrydetails.ui.CountryDetailsViewModel
import dagger.Module
import dagger.Provides

@Module
class CountryDetailsModule {
    
    @Provides
    fun provideCountryDetailsViewModel(
        getCountryDetailsUseCase: GetCountryDetailsUseCase
    ): CountryDetailsViewModel {
        return CountryDetailsViewModel(getCountryDetailsUseCase)
    }
}

