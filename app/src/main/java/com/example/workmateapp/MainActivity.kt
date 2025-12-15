package com.example.workmateapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.workmateapp.di.AppComponent
import com.example.workmateapp.di.DaggerAppComponent
import com.example.workmateapp.feature.countrieslist.ui.CountriesListScreen
import com.example.workmateapp.feature.countrieslist.ui.CountriesListViewModel
import com.example.workmateapp.feature.countrydetails.ui.CountryDetailsScreen
import com.example.workmateapp.feature.countrydetails.ui.CountryDetailsViewModel
import timber.log.Timber

class MainActivity : ComponentActivity() {
    
    private lateinit var appComponent: AppComponent
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        Timber.plant(Timber.DebugTree())
        
        appComponent = DaggerAppComponent.builder()
            .appModule(com.example.workmateapp.di.AppModule(this))
            .build()
        
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    
                    val countriesListViewModelFactory = remember { 
                        appComponent.countriesListViewModelFactory() 
                    }
                    val countryDetailsViewModelFactory = remember { 
                        appComponent.countryDetailsViewModelFactory() 
                    }
                    
                    NavHost(
                        navController = navController,
                        startDestination = "countries_list"
                    ) {
                        composable("countries_list") {
                            val viewModel: CountriesListViewModel = viewModel(
                                factory = countriesListViewModelFactory
                            )
                            CountriesListScreen(
                                viewModel = viewModel,
                                onCountryClick = { countryName ->
                                    navController.navigate("country_details/$countryName")
                                }
                            )
                        }
                        composable("country_details/{countryName}") { backStackEntry ->
                            val countryName = backStackEntry.arguments?.getString("countryName") ?: ""
                            val viewModel: CountryDetailsViewModel = viewModel(
                                factory = countryDetailsViewModelFactory
                            )
                            
                            LaunchedEffect(countryName) {
                                viewModel.loadCountry(countryName)
                            }
                            
                            CountryDetailsScreen(
                                viewModel = viewModel,
                                countryName = countryName,
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

