package com.example.composetest.screens.detail.company.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.screens.detail.company.domain.CompanyDetail
import com.example.composetest.screens.detail.company.domain.GetCompanyDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyDetailViewModel
@Inject constructor(
    private val getCompanyDetailUseCase: GetCompanyDetailUseCase
) : ViewModel() {

    val companyItem: StateFlow<CompanyDetail?> = getCompanyDetailUseCase.companyItem

    fun getCompany(id: Long) = viewModelScope.launch {
        getCompanyDetailUseCase.execute(id)
    }

    private val companyDescription = MutableStateFlow("")

    fun setCompanyDescription(description: String) {
        companyDescription.value = description
    }

    fun updateDescription() = viewModelScope.launch {
        //companyRepository.updateDescription(companyDescription.value, company.value!!.id)
    }

    private val customerName = MutableStateFlow("")
    private val customerDescription = MutableStateFlow("")

    fun setCustomerName(name: String) {
        customerName.value = name
    }

    fun setCustomerDescription(description: String) {
        customerDescription.value = description
    }

    fun addCustomer() {
        //val customerList: MutableList<Customer> = mutableListOf()
        //customerList.addAll(company.value!!.customers)
        //customerList.add(Customer(customerName.value, customerDescription.value))
//
        //viewModelScope.launch {
        //    customerList.sortBy { customer -> customer.name }
        //    companyRepository.updateCustomers(customerList, company.value!!.id)
        //}
    }
}