package com.example.workmateapp.feature.countrydetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workmateapp.common.Result
import com.example.workmateapp.domain.usecase.GetCountryDetailsUseCase
import com.example.workmateapp.feature.countrydetails.ui.mapper.CountryDetailsUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountryDetailsViewModel @Inject constructor(
    private val getCountryDetailsUseCase: GetCountryDetailsUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CountryDetailsUiState())
    val uiState: StateFlow<CountryDetailsUiState> = _uiState.asStateFlow()
    
    fun loadCountry(name: String) {
        viewModelScope.launch {
            _uiState.value = CountryDetailsUiState(result = Result.Loading)
            
            getCountryDetailsUseCase(name)
                .catch { e ->
                    _uiState.value = CountryDetailsUiState(
                        result = Result.Error(e)
                    )
                }
                .collect { country ->
                    val countryUi = CountryDetailsUiMapper.toUi(country)
                    if (countryUi == null) {
                        _uiState.value = CountryDetailsUiState(
                            result = Result.Empty
                        )
                    } else {
                        _uiState.value = CountryDetailsUiState(
                            result = Result.Success(countryUi)
                        )
                    }
                }
        }
        
        viewModelScope.launch {
            try {
                getCountryDetailsUseCase.refresh(name)
            } catch (e: Exception) {
                if (_uiState.value.result is Result.Empty) {
                    _uiState.value = CountryDetailsUiState(
                        result = Result.Error(e)
                    )
                }
            }
        }
    }
    
    fun refresh(name: String) {
        viewModelScope.launch {
            try {
                getCountryDetailsUseCase.refresh(name)
            } catch (e: Exception) {
                _uiState.value = CountryDetailsUiState(
                    result = Result.Error(e)
                )
            }
        }
    }
}

