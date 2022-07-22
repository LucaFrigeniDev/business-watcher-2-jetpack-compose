package com.example.composetest.data.repository

import androidx.annotation.WorkerThread
import com.example.composetest.data.dao.GroupDAO
import com.example.composetest.domain.entities.Group
import com.example.composetest.domain.relations.GroupWithCompanies
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GroupRepository @Inject constructor(val dao: GroupDAO) {

    val groups: Flow<List<Group>> = dao.getGroups()

    val groupWithCompaniesList: Flow<List<GroupWithCompanies>> = dao.getGroupWithCompanies()

    fun getGroup(id: Long): Flow<Group> = dao.getGroup(id)

    fun getGroupId(name: String): Flow<Long> = dao.getGroupId(name)

    fun getGroupLogo(id: Long): Flow<String> = dao.getGroupLogo(id)

    @WorkerThread
    suspend fun insert(name: String, logo: String, description: String) =
        dao.insert(Group(0, name, logo, description))

    @WorkerThread
    suspend fun updateDescription(description: String, id: Long) =
        dao.updateGroupDescription(description, id)
}