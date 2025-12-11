package com.example.workmateapp.data.di

import com.example.workmateapp.domain.repository.CountriesRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface DataComponent {
    fun countriesRepository(): CountriesRepository
}

