package com.example.composetest.screens.lists.business_sector_list.domain

import com.example.composetest.screens.lists.business_sector_list.data.BusinessSectorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class IsBusinessSectorDatabaseFullUseCase @Inject constructor(
    private val repository: BusinessSectorRepository
) {

    private var _isFull = MutableStateFlow(true)
    var isFull: StateFlow<Boolean> = _isFull

    suspend fun execute() {
        repository.businessSectorList.collect {
            _isFull.value = it.size == 5
        }
    }

}