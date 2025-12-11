package com.example.workmateapp.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.workmateapp.core.database.entity.CountryEntity
import com.example.workmateapp.core.database.entity.CurrencyEntity
import com.example.workmateapp.core.database.entity.LanguageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {
    
    @Query("SELECT * FROM countries ORDER BY name")
    fun getAllCountries(): Flow<List<CountryEntity>>
    
    @Query("SELECT * FROM countries WHERE name = :name LIMIT 1")
    fun getCountryByName(name: String): Flow<CountryEntity?>
    
    @Query("SELECT * FROM currencies WHERE countryName = :countryName")
    fun getCurrenciesByCountryName(countryName: String): Flow<List<CurrencyEntity>>
    
    @Query("SELECT * FROM languages WHERE countryName = :countryName")
    fun getLanguagesByCountryName(countryName: String): Flow<List<LanguageEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: CountryEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies: List<CurrencyEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguages(languages: List<LanguageEntity>)
    
    @Query("DELETE FROM currencies WHERE countryName = :countryName")
    suspend fun deleteCurrenciesByCountryName(countryName: String)
    
    @Query("DELETE FROM languages WHERE countryName = :countryName")
    suspend fun deleteLanguagesByCountryName(countryName: String)
    
    @Transaction
    suspend fun insertCountryWithRelations(
        country: CountryEntity,
        currencies: List<CurrencyEntity>,
        languages: List<LanguageEntity>
    ) {
        insertCountry(country)
        deleteCurrenciesByCountryName(country.name)
        deleteLanguagesByCountryName(country.name)
        insertCurrencies(currencies)
        insertLanguages(languages)
    }
    
    @Query("DELETE FROM countries")
    suspend fun deleteAllCountries()
    
    @Transaction
    suspend fun insertAllCountriesWithRelations(
        countries: List<CountryEntity>,
        currencies: List<CurrencyEntity>,
        languages: List<LanguageEntity>
    ) {
        deleteAllCountries()
        countries.forEach { insertCountry(it) }
        insertCurrencies(currencies)
        insertLanguages(languages)
    }
}

