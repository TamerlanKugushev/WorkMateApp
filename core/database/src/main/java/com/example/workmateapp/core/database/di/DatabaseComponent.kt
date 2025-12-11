package com.example.workmateapp.core.database.di

import com.example.workmateapp.core.database.dao.CountryDao
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {
    fun countryDao(): CountryDao
}

