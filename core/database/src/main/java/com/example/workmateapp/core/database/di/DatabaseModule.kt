package com.example.workmateapp.core.database.di

import android.content.Context
import androidx.room.Room
import com.example.workmateapp.core.database.AppDatabase
import com.example.workmateapp.core.database.dao.CountryDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val context: Context) {
    
    @Provides
    @Singleton
    fun provideContext(): Context = context
    
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }
    
    @Provides
    @Singleton
    fun provideCountryDao(database: AppDatabase): CountryDao {
        return database.countryDao()
    }
}

