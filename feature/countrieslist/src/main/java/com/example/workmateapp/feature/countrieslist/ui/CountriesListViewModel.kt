package com.example.workmateapp.feature.countrieslist.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.workmateapp.common.Result
import com.example.workmateapp.domain.usecase.GetCountriesListUseCase
import com.example.workmateapp.feature.countrieslist.ui.mapper.CountryUiMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountriesListViewModel @Inject constructor(
    private val getCountriesListUseCase: GetCountriesListUseCase
) : ViewModel() {
    
    companion object {
        private const val TAG = "CountriesListVM"
    }
    
    private val _uiState = MutableStateFlow(CountriesListUiState())
    val uiState: StateFlow<CountriesListUiState> = _uiState.asStateFlow()
    
    // Paging flow - cached in viewModelScope to survive configuration changes
    val countriesPagingFlow: Flow<PagingData<CountryUi>> = getCountriesListUseCase
        .getCountriesPaged()
        .map { pagingData ->
            pagingData.map { country ->
                CountryUiMapper.toUi(country)
            }
        }
        .cachedIn(viewModelScope)
    
    private var isInitialized = false
    
    init {
        loadCountries()
    }
    
    private fun loadCountries() {
        if (isInitialized) {
            Log.d(TAG, "Already initialized, skipping loadCountries")
            return
        }
        isInitialized = true
        
        viewModelScope.launch {
            Log.d(TAG, "Starting loadCountries with Cache-First strategy")
            
            // Check if we have cache
            val hasCache = try {
                getCountriesListUseCase.hasCache()
            } catch (e: Exception) {
                Log.e(TAG, "Error checking cache", e)
                false
            }
            
            if (hasCache) {
                // Show cached data immediately - no Loading state
                Log.d(TAG, "Cache exists, showing data immediately")
                _uiState.value = CountriesListUiState(
                    result = Result.Success(emptyList()), // Paging will handle the data
                    hasCache = true,
                    isRefreshing = false
                )
                
                // Refresh in background if stale
                refreshInBackground()
            } else {
                // No cache - show loading and fetch from network
                Log.d(TAG, "No cache, showing loading state")
                _uiState.value = CountriesListUiState(
                    result = Result.Loading,
                    hasCache = false,
                    isRefreshing = true
                )
                
                try {
                    getCountriesListUseCase.refresh()
                    Log.d(TAG, "Successfully loaded data from network")
                    _uiState.value = CountriesListUiState(
                        result = Result.Success(emptyList()),
                        hasCache = true,
                        isRefreshing = false
                    )
                } catch (e: Exception) {
                    Log.e(TAG, "Error loading data from network", e)
                    _uiState.value = CountriesListUiState(
                        result = Result.Error(e),
                        hasCache = false,
                        isRefreshing = false
                    )
                }
            }
        }
    }
    
    private fun refreshInBackground() {
        viewModelScope.launch {
            try {
                val wasRefreshed = getCountriesListUseCase.refreshIfStale()
                if (wasRefreshed) {
                    Log.d(TAG, "Cache was stale, refreshed successfully")
                } else {
                    Log.d(TAG, "Cache is still valid, no refresh needed")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error refreshing in background", e)
                // Don't update UI state on background refresh error - keep showing cached data
            }
        }
    }
    
    fun refresh() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)
            try {
                getCountriesListUseCase.refresh()
                Log.d(TAG, "Manual refresh successful")
                _uiState.value = _uiState.value.copy(
                    isRefreshing = false,
                    hasCache = true
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error during manual refresh", e)
                _uiState.value = _uiState.value.copy(
                    isRefreshing = false,
                    result = if (!_uiState.value.hasCache) Result.Error(e) else _uiState.value.result
                )
            }
        }
    }
}
