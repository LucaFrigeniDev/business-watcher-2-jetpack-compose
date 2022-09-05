package com.example.composetest.screens.map.domain

import com.google.android.gms.maps.model.LatLng

data class CompanyMapMarker(
    val id: Long,
    val name: String,
    val city: String,
    val latLng: LatLng,
    val color: String,
    val logo: String
    //color, background, logo
)

