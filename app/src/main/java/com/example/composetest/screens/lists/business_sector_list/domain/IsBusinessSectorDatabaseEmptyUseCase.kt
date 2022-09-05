package com.example.composetest.screens.lists.business_sector_list.domain

import com.example.composetest.screens.lists.business_sector_list.data.BusinessSectorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class IsBusinessSectorDatabaseEmptyUseCase @Inject constructor(
    private val repository: BusinessSectorRepository
) {

    private var _isEmpty = MutableStateFlow(false)
    var isEmpty: StateFlow<Boolean> = _isEmpty

    suspend fun execute() {
        repository.businessSectorList.collect {
            _isEmpty.value = it.isEmpty()
        }
    }
}