package com.example.composetest.screens.lists.company_list.domain

import com.example.composetest.base.screenComponents.filteredBusinessSectors
import com.example.composetest.base.screenComponents.filteredGroups
import com.example.composetest.base.screenComponents.isIndependentChipChecked
import com.example.composetest.base.screenComponents.searchBarFilter
import com.example.composetest.location
import com.example.composetest.screens.lists.business_sector_list.data.BusinessSectorRepository
import com.example.composetest.screens.lists.company_list.data.CompanyRepository
import com.example.composetest.screens.lists.group_list.data.GroupRepository
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCompanyItemsUseCase @Inject constructor(
    private val companyRepository: CompanyRepository,
    private val groupRepository: GroupRepository,
    private val businessSectorRepository: BusinessSectorRepository
) {

    private var _companyList = MutableStateFlow(emptyList<CompanyItem>())
    var companyList: StateFlow<List<CompanyItem>> = _companyList

    suspend fun execute() {
        companyRepository.companyList.collect { companies ->
            businessSectorRepository.businessSectorList.collect { businessSectors ->
                groupRepository.groupList.collect { groups ->

                    val companyItemList = mutableListOf<CompanyItem>()

                    companies.forEach { company ->
                        lateinit var companyItem: CompanyItem

                        businessSectors.forEach { businessSector ->
                            if (businessSector.id == company.businessSectorId)
                                companyItem = CompanyItem(company, businessSector, null)
                        }

                        if (company.groupId != null) {
                            groups.forEach { group ->
                                if (group.id == company.groupId)
                                    companyItem.group = group
                            }
                        }

                        filter(companyItem, companyItemList)
                    }

                    _companyList.value = companyItemList
                    sort(SortType.TURNOVER)
                }
            }
        }
    }

    private fun filter(companyItem: CompanyItem, companyItemList: MutableList<CompanyItem>) {

        if (searchBarFilter.isBlank()) {

            if (filteredBusinessSectors.contains(companyItem.businessSector.id)) {

                if (companyItem.group != null) {
                    if (filteredGroups.contains(companyItem.group!!.id))
                        companyItemList.add(companyItem)

                } else {
                    if (isIndependentChipChecked)
                        companyItemList.add(companyItem)
                }
            }

        } else {
            if (filteredBusinessSectors.contains(companyItem.businessSector.id)) {

                if (companyItem.group != null) {
                    if (
                        filteredGroups.contains(companyItem.group!!.id)
                        && companyItem.group!!.name.contains(searchBarFilter, true)
                        || companyItem.company.name.contains(searchBarFilter, true)
                    ) {
                        companyItemList.add(companyItem)
                    }

                } else {
                    if (
                        isIndependentChipChecked
                        && companyItem.group!!.name.contains(searchBarFilter, true)
                        || companyItem.company.name.contains(searchBarFilter, true)
                    ) {
                        companyItemList.add(companyItem)
                    }
                }
            }
        }
    }

    fun sort(sortType: SortType) = when (sortType) {
        SortType.TURNOVER ->
            _companyList.value =
                companyList.value
                    .sortedByDescending { companyItem -> companyItem.company.turnover }

        SortType.GROUP ->
            _companyList.value =
                companyList.value
                    .sortedWith(compareBy<CompanyItem> { companyItem -> companyItem.company.groupId }
                        .thenByDescending { companyItem -> companyItem.company.turnover })

        SortType.SECTOR ->
            _companyList.value =
                companyList.value
                    .sortedWith(compareBy<CompanyItem> { companyItem -> companyItem.businessSector.id }
                        .thenByDescending { companyItem -> companyItem.company.turnover })

        SortType.DISTANCE ->
            _companyList.value =
                companyList.value.sortedWith(compareBy {
                    SphericalUtil.computeDistanceBetween(
                        location,
                        LatLng(it.company.latitude, it.company.longitude)
                    ).toInt()
                })
    }

    enum class SortType { TURNOVER, GROUP, SECTOR, DISTANCE }
}