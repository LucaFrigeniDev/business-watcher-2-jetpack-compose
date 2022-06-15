package com.example.composetest.ui.screens.creationScreens

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.data.repository.BusinessSectorRepository
import com.example.composetest.data.repository.CompanyRepository
import com.example.composetest.data.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CompanyCreationViewModel
@Inject constructor(
    private val companyRepository: CompanyRepository,
    private val groupRepository: GroupRepository,
    private val sectorRepository: BusinessSectorRepository
) : ViewModel() {

    private val _groupsNames = MutableStateFlow(listOf<String>())
    val groupsNames: StateFlow<List<String>> = _groupsNames

    private val _sectorsNames = MutableStateFlow(listOf<String>())
    val sectorsNames: StateFlow<List<String>> = _sectorsNames

    init {
        viewModelScope.launch {
            sectorRepository.businessSectorList.collect { businessSectors ->
                val names: MutableList<String> = mutableListOf()

                for (businessSector in businessSectors) {
                    names.add(businessSector.name)
                }

                _sectorsNames.value = names.sorted()
            }
        }

        viewModelScope.launch {
            groupRepository.groups.collect { groups ->
                val names: MutableList<String> = mutableListOf()

                for (group in groups) {
                    names.add(group.name)
                }
                names.sorted()
                names.add(0, "Independent")

                _groupsNames.value = names
            }
        }
    }

    private val name = MutableStateFlow("")
    private val businessSector = MutableStateFlow(0L)
    private val address = MutableStateFlow("")
    private val city = MutableStateFlow("")
    private val postalCode = MutableStateFlow("")
    private val turnover = MutableStateFlow(0)
    private val description = MutableStateFlow("")

    private val _group = MutableStateFlow("Independent")
    val group: StateFlow<String> = _group

    private val _groupLogo = MutableStateFlow("")
    val groupLogo: StateFlow<String> = _groupLogo

    private val _independentLogoUri = MutableStateFlow<Uri?>(null)
    val independentLogoUri: StateFlow<Uri?> = _independentLogoUri

    private val _latitude = MutableStateFlow(0.0)
    val latitude: StateFlow<Double> = _latitude

    private val _longitude = MutableStateFlow(0.0)
    val longitude: StateFlow<Double> = _longitude

    fun setName(companyName: String) {
        name.value = companyName
    }

    fun setGroup(companyGroup: String) = viewModelScope.launch {
        if (companyGroup == "Independent") {
            _group.value = "Independent"
            _groupLogo.value = ""
        } else {
            groupRepository.getGroupId(companyGroup).collect { id ->
                _group.value = id.toString()

                groupRepository.getGroupLogo(id).collect { logo ->
                    _groupLogo.value = logo
                    _independentLogoUri.value = null
                }
            }
        }
    }

    fun setSector(companyBusinessSector: String) = viewModelScope.launch {
        sectorRepository.getBusinessSectorId(companyBusinessSector).collect {
            businessSector.value = it
        }
    }

    fun setPostalCode(companyPC: String) {
        postalCode.value = companyPC
    }

    fun setCity(companyCity: String) {
        city.value = companyCity
    }

    fun setAddress(companyAddress: String) {
        address.value = companyAddress
    }

    fun setTurnover(companyTurnover: Int) {
        turnover.value = companyTurnover
    }

    fun setDescription(companyDescription: String) {
        description.value = companyDescription
    }

    fun setIndependentLogo(uri: Uri?) {
        _independentLogoUri.value = uri
    }

    fun isCorrectlyField(): StateFlow<Boolean> {
        val isCorrectlyField = MutableStateFlow(false)

        if (name.value.isBlank() ||
            businessSector.value == 0L ||
            city.value.isBlank() ||
            postalCode.value.isBlank() ||
            address.value.isBlank() ||
            turnover.value == 0 ||
            description.value.isBlank()
        ) {
            if (groupLogo.value.isBlank() && independentLogoUri.value == null) {
                isCorrectlyField.value = false
            }
        } else isCorrectlyField.value = true
        return isCorrectlyField
    }

    fun isCorrectlyGeolocated(): StateFlow<Boolean> {
        val isCorrectlyGeolocated = MutableStateFlow(false)

        isCorrectlyGeolocated.value = latitude.value != 0.0 && longitude.value != 0.0

        return isCorrectlyGeolocated
    }

    fun geoLocate(context: Context) = viewModelScope.launch {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addressList: List<Address> = geocoder.getFromLocationName(
                address.value + " " + city.value + " " + postalCode.value,
                1
            )
            if (addressList.isNotEmpty()) {
                _latitude.value = addressList[0].latitude
                _longitude.value = addressList[0].longitude
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun insert() = viewModelScope.launch {
        val groupId: Long? =
            if (group.value == "Independent") null
            else group.value.toLong()

        companyRepository.createCompany(
            name.value,
            businessSector.value,
            groupId,
            city.value,
            postalCode.value,
            address.value,
            latitude.value,
            longitude.value,
            turnover.value,
            description.value,
            independentLogoUri.value,
            groupLogo.value
        )
    }
}

