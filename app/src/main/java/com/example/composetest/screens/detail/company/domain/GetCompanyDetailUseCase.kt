package com.example.composetest.screens.detail.company.domain

import com.example.composetest.screens.lists.business_sector_list.data.BusinessSectorRepository
import com.example.composetest.screens.lists.company_list.data.CompanyRepository
import com.example.composetest.screens.lists.group_list.data.GroupRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCompanyDetailUseCase @Inject constructor(
    private val companyRepository: CompanyRepository,
    private val groupRepository: GroupRepository,
    private val businessSectorRepository: BusinessSectorRepository,
) {

    private val _companyItem = MutableStateFlow<CompanyDetail?>(null)
    val companyItem: StateFlow<CompanyDetail?> = _companyItem

    suspend fun execute(id: Long) {
        companyRepository.companyList.collect { companies ->
            businessSectorRepository.businessSectorList.collect { businessSectors ->
                groupRepository.groupList.collect { groups ->

                    val company = companies.find { it.id == id }

                    val businessSectorName =
                        businessSectors.find { it.id == company!!.businessSectorId }!!.name

                    val groupName =
                        groups.find { it.id == company?.groupId }?.name ?: ""

                    _companyItem.value = CompanyDetail(company!!, businessSectorName, groupName)
                }
            }
        }
    }
}