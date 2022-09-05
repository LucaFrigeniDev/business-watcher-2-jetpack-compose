package com.example.composetest.screens.map.domain

import com.example.composetest.screens.lists.company_list.domain.GetCompanyItemsUseCase
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCompanyMapMarkerUseCase @Inject constructor(
    private val getCompanyItemsUseCase: GetCompanyItemsUseCase
) {

    private var _markerList = MutableStateFlow(emptyList<CompanyMapMarker>())
    var markerList: StateFlow<List<CompanyMapMarker>> = _markerList

    suspend fun set() = getCompanyItemsUseCase.execute()

    suspend fun execute() {
        getCompanyItemsUseCase.companyList.collect {
            val companyMapMarkerList = mutableListOf<CompanyMapMarker>()

            it.forEach { companyItem ->
                companyMapMarkerList.add(
                    CompanyMapMarker(
                        companyItem.company.id,
                        companyItem.company.name,
                        companyItem.company.city,
                        LatLng(companyItem.company.latitude, companyItem.company.longitude),
                        companyItem.businessSector.color,
                        companyItem.company.logo
                    )
                )

                _markerList.value = companyMapMarkerList
            }
        }
    }
}