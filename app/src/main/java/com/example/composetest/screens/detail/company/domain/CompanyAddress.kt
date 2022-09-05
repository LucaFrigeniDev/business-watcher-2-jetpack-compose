package com.example.composetest.screens.detail.company.domain

data class CompanyAddress(
    val street: String,
    val city: String,
    val postalCode: String,
    val latitude: Double,
    val longitude: Double
)
