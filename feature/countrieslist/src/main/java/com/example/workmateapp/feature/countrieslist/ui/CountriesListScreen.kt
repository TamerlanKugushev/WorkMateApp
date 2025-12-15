package com.example.workmateapp.feature.countrieslist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.workmateapp.common.Result
import com.example.workmateapp.core.ui.components.AppTopAppBar
import com.example.workmateapp.core.ui.components.EmptyView
import com.example.workmateapp.core.ui.components.ErrorView
import com.example.workmateapp.core.ui.components.LoadingView

@Composable
fun CountriesListScreen(
    viewModel: CountriesListViewModel,
    onCountryClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val countries = viewModel.countriesPagingFlow.collectAsLazyPagingItems()
    
    Column(modifier = Modifier.fillMaxSize()) {
        AppTopAppBar(
            title = "Страны",
            showBackButton = false
        )
        
        // Show loading only on initial load when there's no cache
        when {
            // Initial loading state (no cache)
            uiState.result is Result.Loading && !uiState.hasCache -> {
                LoadingView()
            }
            // Error state (no cache available)
            uiState.result is Result.Error && !uiState.hasCache -> {
                ErrorView(
                    message = (uiState.result as Result.Error).exception.message ?: "Ошибка"
                )
            }
            // Show paging content
            else -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    when (countries.loadState.refresh) {
                        is LoadState.Loading -> {
                            if (countries.itemCount == 0 && !uiState.hasCache) {
                                LoadingView()
                            }
                        }
                        is LoadState.Error -> {
                            if (countries.itemCount == 0) {
                                val error = (countries.loadState.refresh as LoadState.Error).error
                                ErrorView(message = error.message ?: "Ошибка загрузки")
                            }
                        }
                        is LoadState.NotLoading -> {
                            if (countries.itemCount == 0) {
                                EmptyView()
                            }
                        }
                    }
                    
                    if (countries.itemCount > 0) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(
                                count = countries.itemCount,
                                key = { index -> countries[index]?.name ?: index }
                            ) { index ->
                                countries[index]?.let { country ->
                                    CountryItem(
                                        country = country,
                                        onClick = { onCountryClick(country.name) }
                                    )
                                }
                            }
                            
                            // Loading indicator at the bottom when loading more
                            when (countries.loadState.append) {
                                is LoadState.Loading -> {
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(32.dp)
                                            )
                                        }
                                    }
                                }
                                is LoadState.Error -> {
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "Ошибка загрузки. Нажмите для повтора",
                                                modifier = Modifier.clickable {
                                                    countries.retry()
                                                }
                                            )
                                        }
                                    }
                                }
                                else -> {}
                            }
                        }
                    }
                    
                    // Show refresh indicator at the top if refreshing in background
                    if (uiState.isRefreshing && countries.itemCount > 0) {
                        LinearProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopCenter)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CountryItem(
    country: CountryUi,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = country.flag,
                contentDescription = country.name,
                modifier = Modifier.size(64.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = country.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                country.capital?.let {
                    Text(
                        text = "Столица: $it",
                        fontSize = 14.sp
                    )
                }
                country.region?.let {
                    Text(
                        text = "Регион: $it",
                        fontSize = 14.sp
                    )
                }
                Text(
                    text = "Население: ${country.population}",
                    fontSize = 14.sp
                )
            }
        }
    }
}
