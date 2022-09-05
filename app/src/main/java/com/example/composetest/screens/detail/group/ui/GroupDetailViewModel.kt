package com.example.composetest.screens.detail.group.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.screens.detail.group.domain.GetGroupDetailUseCase
import com.example.composetest.screens.detail.group.domain.GroupDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel
@Inject constructor(
    private val getGroupDetailUseCase: GetGroupDetailUseCase
) : ViewModel() {

    val group: StateFlow<GroupDetail?> = getGroupDetailUseCase.groupDetail

    init {
        viewModelScope.launch {
            getGroupDetailUseCase.getGroupsWithCompanies()
        }

        viewModelScope.launch {
            getGroupDetailUseCase.getBusinessSectorsWithCompanies()
        }

        viewModelScope.launch {
            getGroupDetailUseCase.getCompanyItems()
        }
    }

    fun getGroup(id: Long) =
        viewModelScope.launch {
            getGroupDetailUseCase.execute(id)
        }

    private val groupDescription = MutableStateFlow("")

    fun setDescription(description: String) {
        //groupDescription.value = description
    }

    fun updateDescription() = viewModelScope.launch {
        // groupRepository.updateDescription(groupDescription.value, group.value!!.id)
    }
}
