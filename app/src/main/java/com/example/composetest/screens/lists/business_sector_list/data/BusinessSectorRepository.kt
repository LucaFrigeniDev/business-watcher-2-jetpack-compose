package com.example.composetest.screens.lists.business_sector_list.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BusinessSectorRepository @Inject constructor(var DAO: BusinessSectorDAO) {

    val businessSectorList = DAO.getBusinessSectors()

    fun getBusinessSectorId(name: String): Flow<Long> = DAO.getBusinessSectorId(name)

    @WorkerThread
    suspend fun insert(businessSector: BusinessSector) =
        DAO.insert(businessSector)
}