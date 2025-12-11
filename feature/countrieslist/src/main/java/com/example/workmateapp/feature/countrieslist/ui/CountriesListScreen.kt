package com.example.workmateapp.feature.countrieslist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    
    Column(modifier = Modifier.fillMaxSize()) {
        AppTopAppBar(
            title = "Страны",
            showBackButton = false
        )
        
        when (val result = uiState.result) {
            is Result.Loading -> LoadingView()
            is Result.Success -> {
                if (result.data.isEmpty()) {
                    EmptyView()
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(result.data) { country ->
                            CountryItem(
                                country = country,
                                onClick = { onCountryClick(country.name) }
                            )
                        }
                    }
                }
            }
            is Result.Error -> ErrorView(message = result.exception.message ?: "Ошибка")
            is Result.Empty -> EmptyView()
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

