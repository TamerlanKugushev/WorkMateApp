package com.example.workmateapp.domain.usecase

import com.example.workmateapp.domain.model.Country
import com.example.workmateapp.domain.repository.CountriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountryDetailsUseCase @Inject constructor(
    private val repository: CountriesRepository
) {
    operator fun invoke(name: String): Flow<Country?> = repository.getCountryByName(name)
    
    suspend fun refresh(name: String) = repository.refreshCountry(name)
}

