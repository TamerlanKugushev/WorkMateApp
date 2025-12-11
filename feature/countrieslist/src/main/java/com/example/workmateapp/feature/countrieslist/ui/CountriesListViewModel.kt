package com.example.workmateapp.feature.countrieslist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workmateapp.common.ErrorHandler
import com.example.workmateapp.common.Result
import com.example.workmateapp.domain.usecase.GetCountriesListUseCase
import com.example.workmateapp.feature.countrieslist.ui.mapper.CountryUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountriesListViewModel @Inject constructor(
    private val getCountriesListUseCase: GetCountriesListUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CountriesListUiState())
    val uiState: StateFlow<CountriesListUiState> = _uiState.asStateFlow()
    
    init {
        loadCountries()
    }
    
    private fun loadCountries() {
        viewModelScope.launch {
            _uiState.value = CountriesListUiState(result = Result.Loading)
            
            getCountriesListUseCase()
                .catch { e ->
                    _uiState.value = CountriesListUiState(
                        result = Result.Error(e)
                    )
                }
                .collect { countries ->
                    if (countries.isEmpty()) {
                        _uiState.value = CountriesListUiState(
                            result = Result.Empty
                        )
                    } else {
                        _uiState.value = CountriesListUiState(
                            result = Result.Success(CountryUiMapper.toUiList(countries))
                        )
                    }
                }
        }
        
        viewModelScope.launch {
            try {
                getCountriesListUseCase.refresh()
            } catch (e: Exception) {
                if (_uiState.value.result is Result.Empty) {
                    _uiState.value = CountriesListUiState(
                        result = Result.Error(e)
                    )
                }
            }
        }
    }
    
    fun refresh() {
        viewModelScope.launch {
            try {
                getCountriesListUseCase.refresh()
            } catch (e: Exception) {
                _uiState.value = CountriesListUiState(
                    result = Result.Error(e)
                )
            }
        }
    }
}

