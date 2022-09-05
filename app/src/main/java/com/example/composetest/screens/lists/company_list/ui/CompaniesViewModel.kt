package com.example.composetest.screens.lists.company_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.screens.lists.company_list.domain.CompanyItem
import com.example.composetest.screens.lists.company_list.domain.GetCompanyItemsUseCase
import com.example.composetest.screens.lists.company_list.domain.GetCompanyItemsUseCase.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompaniesViewModel @Inject constructor(
    private val getCompanyItemsUseCase: GetCompanyItemsUseCase
) : ViewModel() {

    val companyItemList: StateFlow<List<CompanyItem>> = getCompanyItemsUseCase.companyList

    init {
        viewModelScope.launch {
            getCompanyItemsUseCase.execute()
        }
    }

    fun sortCompanies(sortType: SortType) = getCompanyItemsUseCase.sort(sortType)
}