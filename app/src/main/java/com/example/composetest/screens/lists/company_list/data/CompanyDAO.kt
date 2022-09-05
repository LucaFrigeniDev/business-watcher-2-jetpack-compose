package com.example.composetest.screens.lists.company_list.data

import androidx.room.*
import com.example.composetest.screens.detail.company.domain.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanyDAO {

    @Query("SELECT * FROM company_table")
    fun getCompanies(): Flow<List<Company>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(company: Company)

    @Query("UPDATE company_table SET description = :description WHERE id = :id")
    suspend fun updateCompanyDescription(description: String, id: Long)

    @Query("UPDATE company_table SET customers = :customers WHERE id = :id")
    suspend fun updateCompanyCustomers(customers: List<Customer>, id: Long)

    @Delete
    suspend fun delete(company: Company)
}