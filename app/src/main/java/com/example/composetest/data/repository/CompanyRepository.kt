package com.example.composetest.data.repository

import androidx.annotation.WorkerThread
import com.example.composetest.data.dao.CompanyDAO
import com.example.composetest.domain.entities.Company
import com.example.composetest.domain.other.Customer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CompanyRepository @Inject constructor(val dao: CompanyDAO) {

    fun getCompany(id: Long): Flow<Company> = dao.getCompany(id)

    @WorkerThread
    suspend fun insert(company: Company) = dao.insert(company)

    @WorkerThread
    suspend fun updateDescription(description: String, id: Long) =
        dao.updateCompanyDescription(description, id)

    @WorkerThread
    suspend fun updateCustomers(customers: List<Customer>, id: Long) =
        dao.updateCompanyCustomers(customers, id)
}