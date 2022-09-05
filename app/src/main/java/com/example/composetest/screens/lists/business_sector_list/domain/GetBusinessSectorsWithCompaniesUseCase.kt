package com.example.composetest.screens.lists.business_sector_list.domain

import com.example.composetest.screens.lists.business_sector_list.data.BusinessSectorRepository
import com.example.composetest.screens.lists.company_list.data.Company
import com.example.composetest.screens.lists.company_list.data.CompanyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetBusinessSectorsWithCompaniesUseCase @Inject constructor(
    private val businessSectorRepository: BusinessSectorRepository,
    private val companyRepository: CompanyRepository
) {

    private var _list = MutableStateFlow(emptyList<BusinessSectorWithCompanies>())
    var list: StateFlow<List<BusinessSectorWithCompanies>> = _list

    suspend fun execute() {
        companyRepository.companyList.collect { companies ->
            businessSectorRepository.businessSectorList.collect {
                val businessSectorWithCompaniesList = mutableListOf<BusinessSectorWithCompanies>()

                it.forEach { businessSector ->

                    val companyList = mutableListOf<Company>()

                    companies.forEach { company ->
                        if (company.businessSectorId == businessSector.id)
                            companyList.add(company)
                    }

                    val businessSectorWithCompanies =
                        BusinessSectorWithCompanies(businessSector, companyList)

                    businessSectorWithCompaniesList.add(businessSectorWithCompanies)
                }

                _list.value = businessSectorWithCompaniesList
            }
        }
    }

}