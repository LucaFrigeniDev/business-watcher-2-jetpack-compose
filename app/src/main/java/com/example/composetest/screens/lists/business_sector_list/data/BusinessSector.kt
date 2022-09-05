package com.example.composetest.screens.lists.business_sector_list.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "business_sector_table")
data class BusinessSector(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var name: String,
    var color: String,
)