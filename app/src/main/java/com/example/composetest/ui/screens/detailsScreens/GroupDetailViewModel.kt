package com.example.composetest.ui.screens.detailsScreens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.data.repository.BusinessSectorRepository
import com.example.composetest.data.repository.GroupRepository
import com.example.composetest.domain.entities.Company
import com.example.composetest.domain.entities.Group
import com.example.composetest.domain.other.GroupBusinessSector
import com.example.composetest.domain.other.GroupRank
import com.example.composetest.domain.relations.BusinessSectorWithCompanies
import com.example.composetest.domain.relations.GroupWithCompanies
import com.example.composetest.numberFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel
@Inject constructor(
    private val groupRepository: GroupRepository,
    private val businessSectorRepository: BusinessSectorRepository
) : ViewModel() {

    private val _group: MutableStateFlow<Group?> = MutableStateFlow(null)
    val group: StateFlow<Group?> = _group

    private val _companies: MutableStateFlow<List<BusinessSectorWithCompanies>> =
        MutableStateFlow(emptyList())
    val companies: StateFlow<List<BusinessSectorWithCompanies>> = _companies

    private val _turnover: MutableStateFlow<String> = MutableStateFlow("")
    val turnover: StateFlow<String> = _turnover

    private val _rank: MutableStateFlow<String> = MutableStateFlow("")
    val rank: StateFlow<String> = _rank

    private val _numberOfCompanies: MutableStateFlow<String> = MutableStateFlow("")
    val numberOfCompanies: StateFlow<String> = _numberOfCompanies

    private val _businessSectors: MutableStateFlow<List<GroupBusinessSector>> =
        MutableStateFlow(emptyList())
    val businessSectors: StateFlow<List<GroupBusinessSector>> = _businessSectors

    fun getGroup(id: Long) = viewModelScope.launch {
        groupRepository.groupWithCompaniesList.collect { groupsWithCompanies ->
            val groupRanks: MutableList<GroupRank> = mutableListOf()

            getGroupMainInformation(id, groupRanks, groupsWithCompanies)
            getGroupRank(groupRanks, id)
            getGroupCompanies(id)
            getBusinessSectors(id)
        }
    }

    private fun getGroupMainInformation(
        id: Long,
        groupRanks: MutableList<GroupRank>,
        groupsWithCompanies: List<GroupWithCompanies>
    ) {
        for (groupWithCompanies in groupsWithCompanies) {
            val turnover = groupWithCompanies.companies.sumOf { company -> company.turnover }
            val groupRank = GroupRank(groupWithCompanies.group.id, turnover)
            groupRanks.add(groupRank)

            if (groupWithCompanies.group.id == id) {
                _group.value = groupWithCompanies.group
                _numberOfCompanies.value = groupWithCompanies.companies.size.toString()
                _turnover.value = numberFormat(turnover)
            }
        }
    }

    private fun getGroupRank(groupRanks: MutableList<GroupRank>, id: Long) {
        groupRanks.sortByDescending { group -> group.turnover }
        _rank.value =
            (groupRanks.indexOf(groupRanks.find { group -> group.id == id }) + 1).toString()
    }

    private fun getGroupCompanies(id: Long) = viewModelScope.launch {
        businessSectorRepository.getBusinessSectorWithCompanies.collect {
            val businessSectorWithCompanies: MutableList<BusinessSectorWithCompanies> =
                mutableListOf()

            for (businessSectors in it) {
                val businessSector =
                    BusinessSectorWithCompanies(businessSectors.businessSector, mutableListOf())

                for (company in businessSectors.companies) {
                    if (company.groupId == id) businessSector.companies.add(company)
                }
                businessSectorWithCompanies.add(businessSector)
            }

            _companies.value = businessSectorWithCompanies
        }
    }

    private fun getBusinessSectors(id: Long) = viewModelScope.launch {
        val groupBusinessSectorList: MutableList<GroupBusinessSector> = mutableListOf()
        businessSectorRepository.getBusinessSectorWithCompanies.collect { list ->
            groupRepository.groups.collect { groups ->

                for (businessSectorWithCompanies in list) {
                    val companies: MutableList<Company> = mutableListOf()
                    for (company in businessSectorWithCompanies.companies) {
                        if (company.groupId == id)
                            companies.add(company)
                    }

                    val sectorGlobalTurnover =
                        businessSectorWithCompanies.companies.sumOf { it.turnover }
                    val sectorGroupTurnover = companies.sumOf { it.turnover }
                    var share = 0
                    if (sectorGlobalTurnover != 0) {
                        share = sectorGroupTurnover * 100 / sectorGlobalTurnover
                    }

                    if (companies.isNotEmpty()) {
                        groupBusinessSectorList.add(
                            GroupBusinessSector(
                                businessSectorWithCompanies.businessSector.name,
                                businessSectorWithCompanies.businessSector.color,
                                companies.size.toString(),
                                sectorGroupTurnover.toString(),
                                getBusinessSectorRank(
                                    businessSectorWithCompanies,
                                    groups,
                                    id
                                ).toString(),
                                "$share%"
                            )
                        )
                    }
                }
                _businessSectors.value = groupBusinessSectorList
            }
        }
    }

    private fun getBusinessSectorRank(
        businessSectorWithCompanies: BusinessSectorWithCompanies,
        groups: List<Group>,
        id: Long
    ): Int {
        val groupRanks: MutableList<GroupRank> = mutableListOf()

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

    private val groupDescription = MutableStateFlow("")

    fun setDescription(description: String) {
        groupDescription.value = description
    }

    fun updateDescription() = viewModelScope.launch {
        groupRepository.updateDescription(groupDescription.value, group.value!!.id)
    }
}
