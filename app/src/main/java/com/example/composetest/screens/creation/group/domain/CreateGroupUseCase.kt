package com.example.composetest.screens.creation.group.domain

import android.net.Uri
import com.example.composetest.screens.lists.group_list.data.GroupRepository
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(
    private val repository: GroupRepository
) {

    suspend fun execute(name: String, logo: Uri, description: String) {

        val uuid = UUID.randomUUID().toString()

        FirebaseStorage
            .getInstance()
            .getReference(uuid)
            .putFile(logo)

        repository.insert(name, uuid, description)
    }

}