package com.example.composetest.data.repository

import android.net.Uri
import androidx.annotation.WorkerThread
import com.example.composetest.data.dao.GroupDAO
import com.example.composetest.domain.entities.Group
import com.example.composetest.domain.relations.GroupWithCompanies
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class GroupRepository @Inject constructor(val dao: GroupDAO) {

    val groups: Flow<List<Group>> = dao.getGroups()

    val groupWithCompaniesList: Flow<List<GroupWithCompanies>> = dao.getGroupWithCompanies()

    fun getGroup(id: Long): Flow<Group> = dao.getGroup(id)

    fun getGroupId(name: String): Flow<Long> = dao.getGroupId(name)

    fun getGroupLogo(id: Long): Flow<String> = dao.getGroupLogo(id)

    @WorkerThread
    suspend fun insert(name: String, logoUri: Uri?, description: String) {
        val uuid: String = UUID.randomUUID().toString()
        val mImageRef = FirebaseStorage.getInstance().getReference(uuid)

        mImageRef.putFile(logoUri!!).addOnCompleteListener {
            if (it.isSuccessful)
                GlobalScope.launch { dao.insert(Group(0, name, uuid, description)) }
        }
    }

    @WorkerThread
    suspend fun updateDescription(description: String, id: Long) =
        dao.updateGroupDescription(description, id)
}