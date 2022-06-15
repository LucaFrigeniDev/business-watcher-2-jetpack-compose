package com.example.composetest.ui.screens.groupsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.data.repository.GroupRepository
import com.example.composetest.domain.relations.GroupWithCompanies
import com.example.composetest.ui.base.filteredGroups
import com.example.composetest.ui.base.searchBarFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel
@Inject constructor(private val repository: GroupRepository) : ViewModel() {

    private val _groups = MutableStateFlow<List<GroupWithCompanies>>(emptyList())
    val groups: StateFlow<List<GroupWithCompanies>> = _groups

    init {
        viewModelScope.launch {
            repository.groupWithCompaniesList.collect {
                if (filteredGroups.size == it.size && searchBarFilter.isBlank())
                    _groups.value = it
                else {
                    val list: MutableList<GroupWithCompanies> = mutableListOf()

                    for (group in it) {
                        if (searchBarFilter.isBlank()) {
                            if (filteredGroups.contains(group.group.id))
                                list.add(group)

                        } else {
                            if (filteredGroups.contains(group.group.id) &&
                                group.group.name.contains(searchBarFilter, true)
                            )
                                list.add(group)
                        }
                    }

                    _groups.value = list
                }
            }
        }
    }
}