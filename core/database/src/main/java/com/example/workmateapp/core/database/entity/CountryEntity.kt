package com.example.workmateapp.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey
    val name: String,
    val flag: String,
    val capital: String?,
    val population: Long,
    val region: String?,
    val subregion: String?,
    val maps: String?
)

