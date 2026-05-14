package com.example.mindmatrixproject.data

data class Cooperative(
    val id: String,
    val name: String,
    val type: String,
    val locality: String,
    val address: String,
    val openHours: String,
    val publicContact: String,
    val website: String?,
    val story: String,
    val emoji: String,
    val accentHex: Long,
    val lat: Double,
    val lng: Double,
)

data class Artisan(
    val id: String,
    val name: String,
    val cooperativeId: String,
    val village: String,
    val yearsOfCraft: Int,
    val photoEmoji: String,
    val story: String,
    val signatureToys: List<String>,
    val videoTitle: String,
)

data class Toy(
    val id: String,
    val name: String,
    val category: String,
    val artisanId: String,
    val emoji: String,
    val lacColors: List<String>,
    val priceInr: Int,
    val description: String,
    val accentHex: Long,
)