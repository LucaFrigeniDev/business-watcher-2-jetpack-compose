package com.example.composetest.screens.lists.business_sector_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.screens.lists.business_sector_list.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessSectorViewModel @Inject constructor(
    private val isBusinessSectorDatabaseEmptyUseCase: IsBusinessSectorDatabaseEmptyUseCase,
    private val isBusinessSectorDatabaseFullUseCase: IsBusinessSectorDatabaseFullUseCase,
    private val getBusinessSectorsWithCompaniesUseCase: GetBusinessSectorsWithCompaniesUseCase,
    private val createBusinessSectorUseCase: CreateBusinessSectorUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    var isBusinessSectorDBEmpty: StateFlow<Boolean> = isBusinessSectorDatabaseEmptyUseCase.isEmpty
    var isBusinessSectorDBFull: StateFlow<Boolean> = isBusinessSectorDatabaseFullUseCase.isFull
    var businessSectorsWithCompanies: StateFlow<List<BusinessSectorWithCompanies>> =
        getBusinessSectorsWithCompaniesUseCase.list

    init {
        viewModelScope.launch(dispatcher) {
            isBusinessSectorDatabaseEmptyUseCase.execute()
        }

        viewModelScope.launch(dispatcher) {
            isBusinessSectorDatabaseFullUseCase.execute()
        }

        viewModelScope.launch(dispatcher) {
            getBusinessSectorsWithCompaniesUseCase.execute()
        }
    }

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    fun setName(name: String) {
        _name.value = name
    }

    fun createBusinessSector() =
        viewModelScope.launch(dispatcher) {
            createBusinessSectorUseCase.execute(name.value, businessSectorsWithCompanies.value.size)
            _name.value = ""
        }
}