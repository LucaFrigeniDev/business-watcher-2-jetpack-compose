package com.example.composetest.ui.screens.companiesScreens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.*
import com.example.composetest.data.repository.BusinessSectorRepository
import com.example.composetest.data.repository.GroupRepository
import com.example.composetest.domain.entities.BusinessSector
import com.example.composetest.domain.entities.Company
import com.example.composetest.domain.entities.Group
import com.example.composetest.domain.relations.BusinessSectorWithCompanies
import com.example.composetest.ui.base.filteredBusinessSectors
import com.example.composetest.ui.base.filteredGroups
import com.example.composetest.ui.base.isIndependentChipChecked
import com.example.composetest.ui.base.searchBarFilter
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompaniesViewModel
@Inject constructor(
    private val groupRepository: GroupRepository,
    private val sectorRepository: BusinessSectorRepository
) : ViewModel() {

    init {
        viewModelScope.launch {

            sectorRepository.getBusinessSectorWithCompanies.collect { businessSectorsWithCompanies ->
                groupRepository.groups.collect { groups ->

                    getCompanies(businessSectorsWithCompanies)
                    getBusinessSectors(businessSectorsWithCompanies)

                    if (
                        !isIndependentChipChecked ||
                        filteredGroups.size != groups.size ||
                        filteredBusinessSectors.size != businessSectorsWithCompanies.size
                    ) chipsFilter()

                    if (searchBarFilter.isNotBlank())
                        searchBarFilter(groups)

                    sortCompanies(SortType.TURNOVER)
                }
            }
        }
    }

    private val _companies = MutableStateFlow<List<Company>>(emptyList())
    val companies: StateFlow<List<Company>> = _companies

    private val _businessSectors = MutableStateFlow<List<BusinessSector>>(emptyList())
    val businessSectors: StateFlow<List<BusinessSector>> = _businessSectors

    private fun getCompanies(businessSectorWithCompanies: List<BusinessSectorWithCompanies>) {
        val companies: MutableList<Company> = mutableListOf()

        for (businessSector in businessSectorWithCompanies)
            companies.addAll(businessSector.companies)

        _companies.value = companies
    }

    private fun getBusinessSectors(businessSectorWithCompanies: List<BusinessSectorWithCompanies>) {
        val businessSectors: MutableList<BusinessSector> = mutableListOf()

        for (businessSector in businessSectorWithCompanies)
            businessSectors.add(businessSector.businessSector)

        _businessSectors.value = businessSectors
    }

    private fun chipsFilter() {
        val filteredCompanies: MutableList<Company> = mutableListOf()

        for (company in companies.value) {
            if (filteredBusinessSectors.contains(company.businessSectorId)) {

                if (isIndependentChipChecked)
                    if (company.groupId == null)
                        filteredCompanies.add(company)

                if (filteredGroups.contains(company.groupId))
                    filteredCompanies.add(company)
            }
        }

        _companies.value = filteredCompanies
    }

    private fun searchBarFilter(groups: List<Group>) {
        val filteredCompanies: MutableList<Company> = mutableListOf()

        for (company in companies.value) {
            var businessSectorName = ""
            var groupName = ""

            for (group in groups)
                if (group.id == company.groupId)
                    groupName = group.name

            for (businessSector in businessSectors.value)
                if (company.businessSectorId == businessSector.id)
                    businessSectorName = businessSector.name

            if (groupName.contains(searchBarFilter, true)) {
                filteredCompanies.add(company)
            } else if (company.name.contains(searchBarFilter, true)) {
                filteredCompanies.add(company)
            } else if (businessSectorName.contains(searchBarFilter, true)) {
                filteredCompanies.add(company)
            }
        }

        _companies.value = filteredCompanies
    }

    fun sortCompanies(sortType: SortType) = when (sortType) {
        SortType.TURNOVER ->
            _companies.value =
                companies.value
                    .sortedByDescending { company -> company.turnover }

        SortType.GROUP ->
            _companies.value =
                companies.value
                    .sortedWith(compareBy<Company> { company -> company.groupId }
                        .thenByDescending { company -> company.turnover })

        SortType.SECTOR ->
            _companies.value =
                companies.value
                    .sortedWith(compareBy<Company> { company -> company.businessSectorId }
                        .thenByDescending { company -> company.turnover })

        SortType.DISTANCE ->
            _companies.value =
                companies.value.sortedWith(compareByDescending {
                    SphericalUtil.computeDistanceBetween(
                        userLocation.value,
                        LatLng(it.latitude, it.longitude)
                    ).toInt()
                })
    }

    enum class SortType { TURNOVER, GROUP, SECTOR, DISTANCE }

    private val _userLocation = MutableStateFlow(LatLng(0.0, 0.0))
    val userLocation: StateFlow<LatLng> = _userLocation

    fun getUserLocation(context: Context) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) -> {
                viewModelScope.launch {
                    LocationServices.getFusedLocationProviderClient(context)
                        .lastLocation.addOnCompleteListener { task ->
                            if (task.isSuccessful && task.result != null)
                                _userLocation.value =
                                    LatLng(task.result.latitude, task.result.longitude)
                        }
                }
            }
        }
    }

    fun createMarker(context: Context, businessSectorId: Long): BitmapDescriptor {

        val tint = when (businessSectorId.toInt()) {
            1 -> android.graphics.Color.parseColor("#F9C80E")
            2 -> android.graphics.Color.parseColor("#F86624")
            3 -> android.graphics.Color.parseColor("#EA3546")
            4 -> android.graphics.Color.parseColor("#662E9B")
            else -> android.graphics.Color.parseColor("#43BCCD")
        }

        val background = ContextCompat.getDrawable(context, R.drawable.ic_baseline_location_on_24)
        val wrappedDrawable = DrawableCompat.wrap(background!!)
        DrawableCompat.setTint(wrappedDrawable, tint)

        background.setBounds(0, 0, background.intrinsicWidth, background.intrinsicHeight)

        val bitmap = Bitmap.createBitmap(
            background.intrinsicWidth,
            background.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        background.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}