package com.example.composetest.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.screens.lists.business_sector_list.data.BusinessSector
import com.example.composetest.screens.lists.business_sector_list.data.BusinessSectorRepository
import com.example.composetest.screens.lists.group_list.data.Group
import com.example.composetest.screens.lists.group_list.data.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetFiltersViewModel
@Inject constructor(
    businessSectorRepository: BusinessSectorRepository,
    groupRepository: GroupRepository
) : ViewModel() {

    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups: StateFlow<List<Group>> = _groups

    private val _businessSectors = MutableStateFlow<List<BusinessSector>>(emptyList())
    val businessSectors: StateFlow<List<BusinessSector>> = _businessSectors

    init {
        viewModelScope.launch {
            businessSectorRepository.businessSectorList.collect {
                _businessSectors.value = it
            }
        }

        viewModelScope.launch {
            groupRepository.groupList.collect {
                _groups.value =
                    it.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { group -> group.name })
            }
        }
    }

}