package com.example.workmateapp.data.mapper

import com.example.workmateapp.core.database.entity.CountryEntity
import com.example.workmateapp.core.database.entity.CurrencyEntity
import com.example.workmateapp.core.database.entity.LanguageEntity
import com.example.workmateapp.core.network.dto.CountryDto
import com.example.workmateapp.domain.model.Country
import com.example.workmateapp.domain.model.Currency
import com.example.workmateapp.domain.model.Language

object CountryMapper {
    
    fun dtoToDomain(dto: CountryDto): Country {
        return Country(
            name = dto.name.common,
            flag = dto.flags.png,
            capital = dto.capital?.firstOrNull(),
            population = dto.population,
            currencies = dto.currencies?.map { (code, currencyDto) ->
                Currency(
                    code = code,
                    name = currencyDto.name,
                    symbol = currencyDto.symbol
                )
            } ?: emptyList(),
            region = dto.region,
            subregion = dto.subregion,
            languages = dto.languages?.map { (code, name) ->
                Language(
                    code = code,
                    name = name
                )
            } ?: emptyList(),
            maps = dto.maps?.googleMaps
        )
    }
    
    fun entityToDomain(
        entity: CountryEntity,
        currencies: List<CurrencyEntity>,
        languages: List<LanguageEntity>
    ): Country {
        return Country(
            name = entity.name,
            flag = entity.flag,
            capital = entity.capital,
            population = entity.population,
            currencies = currencies.map { currencyEntity ->
                Currency(
                    code = currencyEntity.code,
                    name = currencyEntity.name,
                    symbol = currencyEntity.symbol
                )
            },
            region = entity.region,
            subregion = entity.subregion,
            languages = languages.map { languageEntity ->
                Language(
                    code = languageEntity.code,
                    name = languageEntity.name
                )
            },
            maps = entity.maps
        )
    }
    
    fun domainToEntity(country: Country): CountryEntity {
        return CountryEntity(
            name = country.name,
            flag = country.flag,
            capital = country.capital,
            population = country.population,
            region = country.region,
            subregion = country.subregion,
            maps = country.maps
        )
    }
    
    fun domainToCurrencyEntities(country: Country): List<CurrencyEntity> {
        return country.currencies.map { currency ->
            CurrencyEntity(
                countryName = country.name,
                code = currency.code,
                name = currency.name,
                symbol = currency.symbol
            )
        }
    }
    
    fun domainToLanguageEntities(country: Country): List<LanguageEntity> {
        return country.languages.map { language ->
            LanguageEntity(
                countryName = country.name,
                code = language.code,
                name = language.name
            )
        }
    }
}

