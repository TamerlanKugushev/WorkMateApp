package com.example.workmateapp.feature.countrydetails.di

import com.example.workmateapp.domain.di.DomainComponent
import com.example.workmateapp.feature.countrydetails.ui.CountryDetailsViewModel
import dagger.Component

@Component(
    modules = [CountryDetailsModule::class],
    dependencies = [DomainComponent::class]
)
interface CountryDetailsComponent {
    fun countryDetailsViewModel(): CountryDetailsViewModel
}

