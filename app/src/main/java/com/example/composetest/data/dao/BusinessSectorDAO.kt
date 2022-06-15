package com.example.composetest.data.dao

import androidx.room.*
import com.example.composetest.domain.entities.BusinessSector
import com.example.composetest.domain.relations.BusinessSectorWithCompanies
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessSectorDAO {

    @Query("SELECT * FROM business_sector_table")
    fun getBusinessSectors(): Flow<List<BusinessSector>>

    @Query("SELECT * FROM business_sector_table")
    fun getBusinessSectorWithCompanies(): Flow<List<BusinessSectorWithCompanies>>

    @Query("SELECT COUNT(*) FROM business_sector_table")
    fun getBusinessSectorListSize(): Flow<Int>

    @Query("SELECT * FROM business_sector_table WHERE id = :id")
    fun getBusinessSector(id: Long): Flow<BusinessSector>

    @Query("SELECT id FROM business_sector_table WHERE name = :name")
    fun getBusinessSectorId(name: String): Flow<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(businessSector: BusinessSector)

    @Delete
    suspend fun delete(businessSector: BusinessSector)
}