package com.example.workmateapp.feature.countrydetails.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
fun CountryDetailsScreen(
    viewModel: CountryDetailsViewModel,
    countryName: String,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(modifier = Modifier.fillMaxSize()) {
        AppTopAppBar(
            title = countryName,
            showBackButton = true,
            onBackClick = onBackClick
        )
        
        when (val result = uiState.result) {
            is Result.Loading -> LoadingView()
            is Result.Success -> {
                CountryDetailsContent(country = result.data)
            }
            is Result.Error -> ErrorView(message = result.exception.message ?: "Ошибка")
            is Result.Empty -> EmptyView()
        }
    }
}

@Composable
fun CountryDetailsContent(country: CountryDetailsUi) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            model = country.flag,
            contentDescription = country.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        
        Text(
            text = country.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        country.capital?.let {
            DetailRow(label = "Столица", value = it)
        }
        
        DetailRow(label = "Население", value = country.population.toString())
        
        country.region?.let {
            DetailRow(label = "Регион", value = it)
        }
        
        country.subregion?.let {
            DetailRow(label = "Подрегион", value = it)
        }
        
        if (country.currencies.isNotEmpty()) {
            Text(
                text = "Валюты",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            country.currencies.forEach { currency ->
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(text = "${currency.code}: ${currency.name}")
                    currency.symbol?.let {
                        Text(text = "Символ: $it")
                    }
                }
            }
        }
        
        if (country.languages.isNotEmpty()) {
            Text(
                text = "Языки",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            country.languages.forEach { language ->
                Text(
                    text = "${language.code}: ${language.name}",
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
        
        country.maps?.let {
            Text(
                text = "Карты: $it",
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Text(
            text = value,
            fontSize = 16.sp
        )
    }
}

