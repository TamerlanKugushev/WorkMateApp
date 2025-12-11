package com.example.workmateapp.domain.di

import com.example.workmateapp.domain.usecase.GetCountriesListUseCase
import com.example.workmateapp.domain.usecase.GetCountryDetailsUseCase
import dagger.Component

@Component(modules = [DomainModule::class])
interface DomainComponent {
    fun getCountriesListUseCase(): GetCountriesListUseCase
    fun getCountryDetailsUseCase(): GetCountryDetailsUseCase
}

