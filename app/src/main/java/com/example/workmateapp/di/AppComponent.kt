package com.example.workmateapp.di

import com.example.workmateapp.feature.countrieslist.ui.CountriesListViewModelFactory
import com.example.workmateapp.feature.countrydetails.ui.CountryDetailsViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun countriesListViewModelFactory(): CountriesListViewModelFactory
    fun countryDetailsViewModelFactory(): CountryDetailsViewModelFactory
}

