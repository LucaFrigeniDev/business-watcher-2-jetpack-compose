package com.example.composetest.screens.lists.group_list.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GroupRepository @Inject constructor(private val DAO: GroupDAO) {

    val groupList = DAO.getGroups()

    fun getGroupId(name: String): Flow<Long> = DAO.getGroupId(name)

    fun getGroupLogo(id: Long): Flow<String> = DAO.getGroupLogo(id)

    @WorkerThread
    suspend fun insert(name: String, logo: String, description: String) =
        DAO.insert(Group(0, name, logo, description))

    @WorkerThread
    suspend fun updateDescription(description: String, id: Long) =
        DAO.updateGroupDescription(description, id)
}