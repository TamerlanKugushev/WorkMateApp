package com.example.workmateapp.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workmateapp.core.database.dao.CountryDao
import com.example.workmateapp.core.database.entity.CountryEntity
import com.example.workmateapp.core.database.entity.CurrencyEntity
import com.example.workmateapp.core.database.entity.LanguageEntity

@Database(
    entities = [CountryEntity::class, CurrencyEntity::class, LanguageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
    
    companion object {
        const val DATABASE_NAME = "app_database"
    }
}

