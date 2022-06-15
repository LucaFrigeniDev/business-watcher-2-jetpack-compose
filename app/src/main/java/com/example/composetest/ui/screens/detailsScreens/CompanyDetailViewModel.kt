package com.example.composetest.ui.screens.detailsScreens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.data.repository.BusinessSectorRepository
import com.example.composetest.data.repository.CompanyRepository
import com.example.composetest.data.repository.GroupRepository
import com.example.composetest.domain.entities.Company
import com.example.composetest.domain.other.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyDetailViewModel
@Inject constructor(
    private val companyRepository: CompanyRepository,
    private val groupRepository: GroupRepository,
    private val sectorRepository: BusinessSectorRepository
) : ViewModel() {

    private val _company = MutableStateFlow<Company?>(null)
    val company: StateFlow<Company?> = _company

    private val _businessSectorName = MutableStateFlow("")
    val businessSectorName: StateFlow<String> = _businessSectorName

    private val _groupName = MutableStateFlow("")
    val groupName: StateFlow<String> = _groupName

    fun getCompany(id: Long) = viewModelScope.launch {
        companyRepository.getCompany(id).collect {
            _company.value = it

            getBusinessSectorName(it.businessSectorId)

            if (it.groupId != null) getGroupName(it.groupId!!)
            else _groupName.value = "Independent"
        }
    }

    private fun getBusinessSectorName(id: Long) = viewModelScope.launch {
        sectorRepository.getBusinessSector(id).collect {
            _businessSectorName.value = it.name
        }
    }

    private fun getGroupName(id: Long) = viewModelScope.launch {
        groupRepository.getGroup(id).collect {
            _groupName.value = it.name
        }
    }

    private val companyDescription = MutableStateFlow("")

    fun setCompanyDescription(description: String) {
        companyDescription.value = description
    }

    fun updateDescription() = viewModelScope.launch {
        companyRepository.updateDescription(companyDescription.value, company.value!!.id)
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
        val customerList: MutableList<Customer> = mutableListOf()
        customerList.addAll(company.value!!.customers)
        customerList.add(Customer(customerName.value, customerDescription.value))

        viewModelScope.launch {
            customerList.sortBy { customer -> customer.name }
            companyRepository.updateCustomers(customerList, company.value!!.id)
        }
    }
}