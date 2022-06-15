package com.example.composetest.data.repository

import android.net.Uri
import androidx.annotation.WorkerThread
import com.example.composetest.data.dao.CompanyDAO
import com.example.composetest.domain.entities.Company
import com.example.composetest.domain.other.Customer
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CompanyRepository @Inject constructor(val dao: CompanyDAO) {

    fun getCompany(id: Long): Flow<Company> = dao.getCompany(id)

    fun createCompany(
        name: String,
        businessSector: Long,
        group: Long?,
        city: String,
        postalCode: String,
        address: String,
        latitude: Double,
        longitude: Double,
        turnover: Int,
        description: String,
        uri: Uri?,
        url: String
    ) {
        if (uri == null) {
            insert(
                Company(
                    0,
                    name,
                    businessSector,
                    group,
                    city,
                    postalCode,
                    address,
                    latitude,
                    longitude,
                    turnover,
                    listOf(),
                    description,
                    url
                )
            )
        } else {
            val uuid: String = UUID.randomUUID().toString()
            val mImageRef = FirebaseStorage.getInstance().getReference(uuid)

            mImageRef.putFile(uri).addOnCompleteListener {
                if (it.isSuccessful)
                    insert(
                        Company(
                            0,
                            name,
                            businessSector,
                            group,
                            city,
                            postalCode,
                            address,
                            latitude,
                            longitude,
                            turnover,
                            listOf(),
                            description,
                            uuid
                        )
                    )

            }
        }
    }

    fun insert(company: Company) {
        GlobalScope.launch { dao.insert(company) }
    }

    @WorkerThread
    suspend fun updateDescription(description: String, id: Long) =
        dao.updateCompanyDescription(description, id)

    @WorkerThread
    suspend fun updateCustomers(customers: List<Customer>, id: Long) =
        dao.updateCompanyCustomers(customers, id)
}