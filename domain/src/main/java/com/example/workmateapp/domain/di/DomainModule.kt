package com.example.workmateapp.domain.di

import com.example.workmateapp.domain.repository.CountriesRepository
import com.example.workmateapp.domain.usecase.GetCountriesListUseCase
import com.example.workmateapp.domain.usecase.GetCountryDetailsUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule(private val repository: CountriesRepository) {
    
    @Provides
    fun provideGetCountriesListUseCase(): GetCountriesListUseCase {
        return GetCountriesListUseCase(repository)
    }
    
    @Provides
    fun provideGetCountryDetailsUseCase(): GetCountryDetailsUseCase {
        return GetCountryDetailsUseCase(repository)
    }
}

