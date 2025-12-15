package com.example.workmateapp.domain.usecase

import androidx.paging.PagingData
import com.example.workmateapp.domain.model.Country
import com.example.workmateapp.domain.repository.CountriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountriesListUseCase @Inject constructor(
    private val repository: CountriesRepository
) {
    operator fun invoke(): Flow<List<Country>> = repository.getCountries()
    
    fun getCountriesPaged(): Flow<PagingData<Country>> = repository.getCountriesPaged()
    
    suspend fun refresh() = repository.refreshCountries()
    
    suspend fun refreshIfStale() = repository.refreshCountriesIfStale()
    
    suspend fun hasCache() = repository.hasCache()
}

