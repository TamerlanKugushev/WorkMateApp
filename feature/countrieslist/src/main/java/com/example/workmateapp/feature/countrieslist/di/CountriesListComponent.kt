package com.example.workmateapp.feature.countrieslist.di

import com.example.workmateapp.domain.di.DomainComponent
import com.example.workmateapp.feature.countrieslist.ui.CountriesListViewModelFactory
import dagger.Component

@Component(
    modules = [CountriesListModule::class],
    dependencies = [DomainComponent::class]
)
interface CountriesListComponent {
    fun countriesListViewModelFactory(): CountriesListViewModelFactory
}

