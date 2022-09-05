package com.example.composetest.screens.lists.group_list.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "group_table")
data class Group(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var name: String,
    var logo: String,
    var description: String,
)