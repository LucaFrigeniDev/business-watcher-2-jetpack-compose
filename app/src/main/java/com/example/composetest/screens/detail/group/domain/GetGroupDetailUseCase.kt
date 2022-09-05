package com.example.composetest.screens.detail.group.domain

import com.example.composetest.screens.lists.business_sector_list.domain.BusinessSectorWithCompanies
import com.example.composetest.screens.lists.business_sector_list.domain.GetBusinessSectorsWithCompaniesUseCase
import com.example.composetest.screens.lists.company_list.data.Company
import com.example.composetest.screens.lists.company_list.domain.CompanyItem
import com.example.composetest.screens.lists.company_list.domain.GetCompanyItemsUseCase
import com.example.composetest.screens.lists.group_list.data.Group
import com.example.composetest.screens.lists.group_list.domain.GetGroupsWithCompaniesUseCase
import com.example.composetest.screens.lists.group_list.domain.GroupWithCompanies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetGroupDetailUseCase @Inject constructor(
    private val getGroupsWithCompaniesUseCase: GetGroupsWithCompaniesUseCase,
    private val getCompanyItemsUseCase: GetCompanyItemsUseCase,
    private val getBusinessSectorsWithCompaniesUseCase: GetBusinessSectorsWithCompaniesUseCase
) {

    private val _groupDetail = MutableStateFlow<GroupDetail?>(null)
    val groupDetail: StateFlow<GroupDetail?> = _groupDetail

    private val businessSectorWithCompaniesList = getBusinessSectorsWithCompaniesUseCase.list
    private val companyItemsList = getCompanyItemsUseCase.companyList
    private val groupWithCompaniesList = getGroupsWithCompaniesUseCase.groupList

    suspend fun getGroupsWithCompanies() = getGroupsWithCompaniesUseCase.execute()

    suspend fun getBusinessSectorsWithCompanies() = getBusinessSectorsWithCompaniesUseCase.execute()

    suspend fun getCompanyItems() = getCompanyItemsUseCase.execute()

    fun execute(id: Long) {

        val groupWithCompanies = groupWithCompaniesList.value.find { id == it.group.id }

        if (groupWithCompanies != null) {
            val group = groupWithCompanies.group
            val turnover = groupWithCompanies.companies.sumOf { it.turnover }.toString()
            val numberOfCompanies = groupWithCompanies.companies.size.toString()
            val rank = getGroupRank(id, groupWithCompaniesList.value)

            val companies = mutableListOf<CompanyItem>()
            getGroupCompanies(id, companies, companyItemsList.value)

            val groupBusinessSectorStatsList = mutableListOf<GroupBusinessSectorStats>()

            getGroupsBusinessSectorStatsList(
                id,
                businessSectorWithCompaniesList.value,
                groupBusinessSectorStatsList,
                groupWithCompaniesList.value
            )

            _groupDetail.value = GroupDetail(
                group,
                rank,
                turnover,
                numberOfCompanies,
                groupBusinessSectorStatsList,
                companies
            )
        }
    }

    private fun getGroupRank(id: Long, groupWithCompaniesList: List<GroupWithCompanies>): String {
        val groupsRanks = mutableListOf<GroupRank>()

        groupWithCompaniesList.forEach {
            groupsRanks.add(
                GroupRank(
                    it.group.id,
                    it.companies.sumOf { company -> company.turnover }
                )
            )
        }

        groupsRanks.sortByDescending { it.turnover }
        return (groupsRanks.indexOf(groupsRanks.find { it.id == id }) + 1).toString()
    }

    private fun getGroupCompanies(
        id: Long,
        filteredCompanyList: MutableList<CompanyItem>,
        companyItemsToFilter: List<CompanyItem>
    ) {
        companyItemsToFilter.forEach {
            if (it.group?.id == id)
                filteredCompanyList.add(it)
        }

        filteredCompanyList.sortedWith(
            compareBy<CompanyItem> { companyItem -> companyItem.company.businessSectorId }
                .thenByDescending { companyItem -> companyItem.company.turnover }
        )
    }

    private fun getGroupsBusinessSectorStatsList(
        id: Long,
        businessSectorWithCompaniesList: List<BusinessSectorWithCompanies>,
        groupBusinessSectorStatsList: MutableList<GroupBusinessSectorStats>,
        groupWithCompaniesList: List<GroupWithCompanies>
    ) {
        businessSectorWithCompaniesList.forEach {

            val filteredCompanies = mutableListOf<Company>()

            it.companies.forEach { company ->
                if (company.groupId == id)
                    filteredCompanies.add(company)
            }

            if (filteredCompanies.isNotEmpty()) {

                val groupTurnover = filteredCompanies.sumOf { company -> company.turnover }
                val businessSectorTurnover = it.companies.sumOf { company -> company.turnover }
                val share = businessSectorTurnover / groupTurnover * 100
                val rank = getBusinessSectorRank(it, groupWithCompaniesList, id)

                groupBusinessSectorStatsList.add(
                    GroupBusinessSectorStats(
                        it.businessSector.name,
                        it.businessSector.color,
                        filteredCompanies.size.toString(),
                        groupTurnover.toString(),
                        rank.toString(),
                        share.toString()
                    )
                )
            }
        }
    }

    private fun getBusinessSectorRank(
        businessSectorWithCompanies: BusinessSectorWithCompanies,
        groupWithCompaniesList: List<GroupWithCompanies>,
        id: Long
    ): Int {
        val groupRanks: MutableList<GroupRank> = mutableListOf()

        val groups = mutableListOf<Group>()
        groupWithCompaniesList.forEach {
            groups.add(it.group)
        }

        for (company in businessSectorWithCompanies.companies) {
            if (company.groupId == null) {
                groupRanks.add(GroupRank(company.id, company.turnover))
            }
        }

        for (group in groups) {
            val companies: MutableList<Company> = mutableListOf()
            for (company in businessSectorWithCompanies.companies) {
                if (company.groupId == group.id) {
                    companies.add(company)
                }
            }

            if (companies.isNotEmpty()) {
                groupRanks.add(
                    GroupRank(
                        companies[0].groupId!!.toLong(),
                        companies.sumOf { it.turnover }
                    )
                )
            }
        }
        groupRanks.sortByDescending { it.turnover }
        return groupRanks.indexOf(groupRanks.find { it.id == id }) + 1
    }

}