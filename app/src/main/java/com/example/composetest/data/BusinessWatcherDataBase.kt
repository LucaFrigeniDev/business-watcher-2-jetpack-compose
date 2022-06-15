package com.example.composetest.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.composetest.data.dao.BusinessSectorDAO
import com.example.composetest.data.dao.CompanyDAO
import com.example.composetest.data.dao.GroupDAO
import com.example.composetest.domain.entities.BusinessSector
import com.example.composetest.domain.entities.Company
import com.example.composetest.domain.entities.Group

@Database(
    entities = [BusinessSector::class, Group::class, Company::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class BusinessWatcherDataBase : RoomDatabase() {
    abstract fun businessSectorDao(): BusinessSectorDAO
    abstract fun groupDao(): GroupDAO
    abstract fun companyDao(): CompanyDAO
}