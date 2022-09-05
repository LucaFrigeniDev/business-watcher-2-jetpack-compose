package com.example.composetest.screens.creation.company.domain

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import com.example.composetest.screens.detail.company.domain.CompanyAddress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*
import javax.inject.Inject

class CheckBeforeCompanyCreationUseCase @Inject constructor(
) {

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    val companyAddress =
        MutableStateFlow(
            CompanyAddress("", "", "", 0.0, 0.0)
        )

    suspend fun execute(
        context: Context,
        name: String,
        businessSector: Long,
        street: String,
        city: String,
        postalCode: String,
        turnover: Int,
        description: String,
        groupLogo: String,
        independentLogoUri: Uri?
    ) {

        _error.value = ""

        isCorrectlyField(
            name,
            businessSector,
            street,
            city,
            postalCode,
            turnover,
            description,
            groupLogo,
            independentLogoUri
        )

        if (error.value.isEmpty()) {
            geoLocate(context, street, city, postalCode)
            isCorrectlyGeolocated()
        }
    }

    private fun isCorrectlyField(
        name: String,
        businessSector: Long,
        street: String,
        city: String,
        postalCode: String,
        turnover: Int,
        description: String,
        groupLogo: String,
        independentLogoUri: Uri?
    ) {

        if (name.isBlank()) {
            _error.value = "name field is empty"
        } else if (businessSector == 0L) {
            _error.value = "select a businessSector"
        } else if (city.isBlank()) {
            _error.value = "city field is empty"
        } else if (postalCode.isBlank()) {
            _error.value = "PC field is empty"
        } else if (street.isBlank()) {
            _error.value = "street field is empty"
        } else if (turnover == 0) {
            _error.value = "turnover field is empty"
        } else if (description.isBlank()) {
            _error.value = "description field is empty"
        } else if (groupLogo.isBlank() && independentLogoUri == null) {
            _error.value = "no logo found"
        }
    }

    private fun isCorrectlyGeolocated() {
        if (companyAddress.value.latitude != 0.0 && companyAddress.value.longitude != 0.0) {
            _error.value = "company registered"
        } else
            _error.value = "geolocation failed"
    }

    private suspend fun geoLocate(
        context: Context,
        street: String,
        city: String,
        postalCode: String
    ) =
        withContext(Dispatchers.Main) {

            try {

                val addressList: List<Address> =
                    Geocoder(context, Locale.getDefault())
                        .getFromLocationName("$street $city $postalCode", 1)

                if (addressList.isNotEmpty()) {
                    companyAddress.value = CompanyAddress(
                        street,
                        city,
                        postalCode,
                        addressList[0].latitude,
                        addressList[0].longitude,
                    )
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
}