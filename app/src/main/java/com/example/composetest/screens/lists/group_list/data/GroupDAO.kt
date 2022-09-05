package com.example.composetest.screens.lists.group_list.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDAO {

    @Query("SELECT * FROM group_table ORDER BY name DESC")
    fun getGroups(): Flow<List<Group>>

    @Query("SELECT * FROM group_table WHERE id = :id")
    fun getGroup(id: Long): Flow<Group>

    @Query("SELECT id FROM group_table WHERE name = :name")
    fun getGroupId(name: String): Flow<Long>

    @Query("SELECT logo FROM group_table WHERE id = :id")
    fun getGroupLogo(id: Long): Flow<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(group: Group)

    @Query("UPDATE group_table SET description = :description WHERE id = :id")
    suspend fun updateGroupDescription(description: String, id: Long)

    @Delete
    suspend fun delete(group: Group)
}