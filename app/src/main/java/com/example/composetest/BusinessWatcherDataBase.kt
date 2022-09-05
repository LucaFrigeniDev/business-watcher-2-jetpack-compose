package com.example.composetest

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.composetest.screens.lists.business_sector_list.data.BusinessSector
import com.example.composetest.screens.lists.business_sector_list.data.BusinessSectorDAO
import com.example.composetest.screens.lists.company_list.data.Company
import com.example.composetest.screens.lists.company_list.data.CompanyDAO
import com.example.composetest.screens.lists.group_list.data.Group
import com.example.composetest.screens.lists.group_list.data.GroupDAO

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