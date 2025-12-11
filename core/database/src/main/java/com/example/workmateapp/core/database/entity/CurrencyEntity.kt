package com.example.workmateapp.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "currencies",
    foreignKeys = [
        ForeignKey(
            entity = CountryEntity::class,
            parentColumns = ["name"],
            childColumns = ["countryName"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["countryName"])]
)
data class CurrencyEntity(
    @androidx.room.PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val countryName: String,
    val code: String,
    val name: String,
    val symbol: String?
)

