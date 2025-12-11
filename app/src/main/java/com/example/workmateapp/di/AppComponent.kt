package com.example.workmateapp.di

import com.example.workmateapp.feature.countrieslist.di.CountriesListComponent
import com.example.workmateapp.feature.countrydetails.di.CountryDetailsComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun countriesListComponent(): CountriesListComponent
    fun countryDetailsComponent(): CountryDetailsComponent
}

