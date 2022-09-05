package com.example.composetest.screens.lists.business_sector_list.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessSectorDAO {

    @Query("SELECT * FROM business_sector_table")
    fun getBusinessSectors(): Flow<List<BusinessSector>>

    @Query("SELECT id FROM business_sector_table WHERE name = :name")
    fun getBusinessSectorId(name: String): Flow<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(businessSector: BusinessSector)

    @Delete
    suspend fun delete(businessSector: BusinessSector)
}