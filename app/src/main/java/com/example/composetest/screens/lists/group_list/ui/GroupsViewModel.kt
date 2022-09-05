package com.example.composetest.screens.lists.group_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.screens.lists.group_list.domain.GetGroupsWithCompaniesUseCase
import com.example.composetest.screens.lists.group_list.domain.GroupWithCompanies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val getGroupsWithCompaniesUseCase: GetGroupsWithCompaniesUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    val groups: StateFlow<List<GroupWithCompanies>> = getGroupsWithCompaniesUseCase.groupList

    init {
        viewModelScope.launch(dispatcher) {
            getGroupsWithCompaniesUseCase.execute()
        }
    }
}