package com.example.workmateapp.data.datasource

import com.example.workmateapp.core.database.dao.CountryDao
import com.example.workmateapp.data.mapper.CountryMapper
import com.example.workmateapp.domain.model.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val dao: CountryDao
) {
    fun getCountries(): Flow<List<Country>> {
        return dao.getAllCountries().flatMapLatest { countryEntities ->
            if (countryEntities.isEmpty()) {
                kotlinx.coroutines.flow.flowOf(emptyList())
            } else {
                val countryFlows = countryEntities.map { countryEntity ->
                    combine(
                        dao.getCurrenciesByCountryName(countryEntity.name),
                        dao.getLanguagesByCountryName(countryEntity.name)
                    ) { currencies, languages ->
                        CountryMapper.entityToDomain(
                            countryEntity,
                            currencies,
                            languages
                        )
                    }
                }
                when {
                    countryFlows.isEmpty() -> kotlinx.coroutines.flow.flowOf(emptyList())
                    countryFlows.size == 1 -> countryFlows[0].map { listOf(it) }
                    else -> combine(countryFlows) { countries -> countries.toList() }
                }
            }
        }
    }
    
    suspend fun getCountriesOnce(): List<Country> {
        val countryEntities = dao.getAllCountriesOnce()
        return countryEntities.map { countryEntity ->
            val currencies = dao.getCurrenciesByCountryName(countryEntity.name).first()
            val languages = dao.getLanguagesByCountryName(countryEntity.name).first()
            CountryMapper.entityToDomain(countryEntity, currencies, languages)
        }
    }
    
    suspend fun getCountriesPaged(limit: Int, offset: Int): List<Country> {
        val countryEntities = dao.getCountriesPaged(limit, offset)
        return countryEntities.map { countryEntity ->
            val currencies = dao.getCurrenciesByCountryName(countryEntity.name).first()
            val languages = dao.getLanguagesByCountryName(countryEntity.name).first()
            CountryMapper.entityToDomain(countryEntity, currencies, languages)
        }
    }
    
    suspend fun getCountriesCount(): Int {
        return dao.getCountriesCount()
    }
    
    fun getCountryByName(name: String): Flow<Country?> {
        return combine(
            dao.getCountryByName(name),
            dao.getCurrenciesByCountryName(name),
            dao.getLanguagesByCountryName(name)
        ) { countryEntity, currencies, languages ->
            countryEntity?.let {
                CountryMapper.entityToDomain(it, currencies, languages)
            }
        }
    }
    
    suspend fun saveCountries(countries: List<Country>) {
        try {
            val countryEntities = countries.map { CountryMapper.domainToEntity(it) }
            val currencyEntities = countries.flatMap { CountryMapper.domainToCurrencyEntities(it) }
            val languageEntities = countries.flatMap { CountryMapper.domainToLanguageEntities(it) }
            
            dao.insertAllCountriesWithRelations(
                countryEntities,
                currencyEntities,
                languageEntities
            )
        } catch (e: Exception) {
            throw e
        }
    }
    
    suspend fun saveCountry(country: Country) {
        val countryEntity = CountryMapper.domainToEntity(country)
        val currencyEntities = CountryMapper.domainToCurrencyEntities(country)
        val languageEntities = CountryMapper.domainToLanguageEntities(country)
        
        dao.insertCountryWithRelations(
            countryEntity,
            currencyEntities,
            languageEntities
        )
    }
}

