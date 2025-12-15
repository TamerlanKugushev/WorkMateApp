package com.example.workmateapp.di

import android.content.Context
import com.example.workmateapp.core.database.di.DaggerDatabaseComponent
import com.example.workmateapp.core.database.di.DatabaseComponent
import com.example.workmateapp.core.database.di.DatabaseModule
import com.example.workmateapp.core.network.di.DaggerNetworkComponent
import com.example.workmateapp.core.network.di.NetworkComponent
import com.example.workmateapp.core.network.di.NetworkModule
import com.example.workmateapp.data.di.DaggerDataComponent
import com.example.workmateapp.data.di.DataComponent
import com.example.workmateapp.data.di.DataModule
import com.example.workmateapp.domain.di.DaggerDomainComponent
import com.example.workmateapp.domain.di.DomainComponent
import com.example.workmateapp.domain.di.DomainModule
import com.example.workmateapp.feature.countrieslist.di.CountriesListModule
import com.example.workmateapp.feature.countrieslist.di.DaggerCountriesListComponent
import com.example.workmateapp.feature.countrieslist.ui.CountriesListViewModelFactory
import com.example.workmateapp.feature.countrydetails.di.CountryDetailsModule
import com.example.workmateapp.feature.countrydetails.di.DaggerCountryDetailsComponent
import com.example.workmateapp.feature.countrydetails.ui.CountryDetailsViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {
    
    @Provides
    @Singleton
    fun provideContext(): Context = context
    
    @Provides
    @Singleton
    fun provideNetworkComponent(): NetworkComponent {
        return DaggerNetworkComponent.builder()
            .networkModule(NetworkModule())
            .build()
    }
    
    @Provides
    @Singleton
    fun provideDatabaseComponent(): DatabaseComponent {
        return DaggerDatabaseComponent.builder()
            .databaseModule(DatabaseModule(context))
            .build()
    }
    
    @Provides
    @Singleton
    fun provideDataComponent(
        networkComponent: NetworkComponent,
        databaseComponent: DatabaseComponent
    ): DataComponent {
        return DaggerDataComponent.builder()
            .dataModule(DataModule(context, networkComponent, databaseComponent))
            .build()
    }
    
    @Provides
    @Singleton
    fun provideDomainComponent(
        dataComponent: DataComponent
    ): DomainComponent {
        return DaggerDomainComponent.builder()
            .domainModule(DomainModule(dataComponent.countriesRepository()))
            .build()
    }
    
    @Provides
    @Singleton
    fun provideCountriesListViewModelFactory(
        domainComponent: DomainComponent
    ): CountriesListViewModelFactory {
        return DaggerCountriesListComponent.builder()
            .domainComponent(domainComponent)
            .countriesListModule(CountriesListModule())
            .build()
            .countriesListViewModelFactory()
    }
    
    @Provides
    @Singleton
    fun provideCountryDetailsViewModelFactory(
        domainComponent: DomainComponent
    ): CountryDetailsViewModelFactory {
        return DaggerCountryDetailsComponent.builder()
            .domainComponent(domainComponent)
            .countryDetailsModule(CountryDetailsModule())
            .build()
            .countryDetailsViewModelFactory()
    }
}

