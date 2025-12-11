package com.example.workmateapp.domain.usecase

import com.example.workmateapp.domain.model.Country
import com.example.workmateapp.domain.repository.CountriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountriesListUseCase @Inject constructor(
    private val repository: CountriesRepository
) {
    operator fun invoke(): Flow<List<Country>> = repository.getCountries()
    
    suspend fun refresh() = repository.refreshCountries()
}

