package com.example.composetest.data.repository

import androidx.annotation.WorkerThread
import com.example.composetest.data.dao.BusinessSectorDAO
import com.example.composetest.domain.entities.BusinessSector
import com.example.composetest.domain.relations.BusinessSectorWithCompanies
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BusinessSectorRepository @Inject constructor(val dao: BusinessSectorDAO) {

    val businessSectorList: Flow<List<BusinessSector>> = dao.getBusinessSectors()

    val getBusinessSectorWithCompanies: Flow<List<BusinessSectorWithCompanies>> =
        dao.getBusinessSectorWithCompanies()

    val businessSectorListSize: Flow<Int> = dao.getBusinessSectorListSize()

    fun getBusinessSectorId(name: String): Flow<Long> = dao.getBusinessSectorId(name)

    fun getBusinessSector(id: Long): Flow<BusinessSector> = dao.getBusinessSector(id)

    @WorkerThread
    suspend fun insert(businessSector: BusinessSector) = dao.insert(businessSector)
}