package com.example.composetest.ui.screens.businessSectorsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.data.repository.BusinessSectorRepository
import com.example.composetest.domain.entities.BusinessSector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessSectorViewModel
@Inject constructor(private val repository: BusinessSectorRepository) : ViewModel() {

    init {
        viewModelScope.launch { repository.businessSectorListSize.collect { _size.value = it } }
    }

    fun businessSectors() = repository.getBusinessSectorWithCompanies

    private val _size = MutableStateFlow(0)
    var size: StateFlow<Int> = _size

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    fun setName(name: String) {
        _name.value = name
    }

    fun insert() = viewModelScope.launch {
        if (name.value.isNotBlank())
            repository.insert(businessSector())
    }

    private fun businessSector() = when (size.value) {
        0 -> BusinessSector(0, name.value, "#F9C80E")
        1 -> BusinessSector(0, name.value, "#F86624")
        2 -> BusinessSector(0, name.value, "#EA3546")
        3 -> BusinessSector(0, name.value, "#662E9B")
        else -> BusinessSector(0, name.value, "#43BCCD")
    }
}