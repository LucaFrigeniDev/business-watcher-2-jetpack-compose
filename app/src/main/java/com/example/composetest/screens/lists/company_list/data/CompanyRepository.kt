package com.example.composetest.screens.lists.company_list.data

import androidx.annotation.WorkerThread
import com.example.composetest.screens.detail.company.domain.Customer
import javax.inject.Inject

class CompanyRepository @Inject constructor(private val DAO: CompanyDAO) {

    val companyList = DAO.getCompanies()

    @WorkerThread
    suspend fun insert(company: Company) = DAO.insert(company)

    @WorkerThread
    suspend fun updateDescription(description: String, id: Long) =
        DAO.updateCompanyDescription(description, id)

    @WorkerThread
    suspend fun updateCustomers(customers: List<Customer>, id: Long) =
        DAO.updateCompanyCustomers(customers, id)
}