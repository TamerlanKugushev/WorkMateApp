package com.example.workmateapp.core.network.di

import com.example.workmateapp.core.network.api.CountriesApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {
    fun countriesApi(): CountriesApi
}

