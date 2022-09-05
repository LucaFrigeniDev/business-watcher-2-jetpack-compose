package com.example.composetest.screens.lists.group_list.domain

import com.example.composetest.base.screenComponents.filteredGroups
import com.example.composetest.base.screenComponents.searchBarFilter
import com.example.composetest.screens.lists.company_list.data.Company
import com.example.composetest.screens.lists.company_list.data.CompanyRepository
import com.example.composetest.screens.lists.group_list.data.Group
import com.example.composetest.screens.lists.group_list.data.GroupRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetGroupsWithCompaniesUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
    private val companyRepository: CompanyRepository
) {

    private var _groupList = MutableStateFlow(emptyList<GroupWithCompanies>())
    var groupList: StateFlow<List<GroupWithCompanies>> = _groupList

    suspend fun execute() {
        companyRepository.companyList.collect { companies ->
            groupRepository.groupList.collect { groups ->
                val groupWithCompaniesList = mutableListOf<GroupWithCompanies>()

                groups.forEach { group ->

                    val companyList = mutableListOf<Company>()

                    if (searchBarFilter.isBlank()) {
                        if (filteredGroups.contains(group.id)) {
                            setGroupWithCompaniesList(
                                group,
                                companies,
                                companyList,
                                groupWithCompaniesList
                            )
                        }

                    } else {
                        if (
                            filteredGroups.contains(group.id) &&
                            group.name.contains(searchBarFilter, true)
                        ) {
                            setGroupWithCompaniesList(
                                group,
                                companies,
                                companyList,
                                groupWithCompaniesList
                            )
                        }
                    }
                }

                _groupList.value = groupWithCompaniesList
            }
        }
    }

    private fun setGroupWithCompaniesList(
        group: Group,
        companiesToFiltered: List<Company>,
        filteredCompanyList: MutableList<Company>,
        groupWithCompaniesList: MutableList<GroupWithCompanies>
    ) {
        companiesToFiltered.forEach { company ->
            if (company.groupId == group.id)
                filteredCompanyList.add(company)
        }

        val groupWithCompanies =
            GroupWithCompanies(group, filteredCompanyList)

        groupWithCompaniesList.add(groupWithCompanies)
    }

}