package com.example.composetest.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.data.repository.BusinessSectorRepository
import com.example.composetest.data.repository.GroupRepository
import com.example.composetest.domain.entities.Group
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

    init {
        viewModelScope.launch {
            groupRepository.groups.collect {
                _groups.value =
                    it.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { group -> group.name })
            }
        }
    }

    val businessSectors = businessSectorRepository.businessSectorList
}