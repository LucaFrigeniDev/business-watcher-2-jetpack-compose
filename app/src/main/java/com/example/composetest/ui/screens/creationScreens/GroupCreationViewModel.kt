package com.example.composetest.ui.screens.creationScreens

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.data.repository.GroupRepository
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class GroupCreationViewModel
@Inject constructor(private val repository: GroupRepository) : ViewModel() {

    private val name = MutableStateFlow("")
    private val description = MutableStateFlow("")

    private val _logo = MutableStateFlow<Uri?>(null)
    val logo: StateFlow<Uri?> = _logo

    fun setName(groupName: String) {
        name.value = groupName
    }

    fun setDescription(groupDescription: String) {
        description.value = groupDescription
    }

    fun setLogo(logo: Uri?) {
        _logo.value = logo
    }

    fun isCorrectlyField(): StateFlow<String> {
        val error = MutableStateFlow("")
        if (name.value.isBlank() && description.value.isNotBlank() && logo.value != null) {
            error.value = "name field is blank"
        } else if (description.value.isBlank() && name.value.isNotBlank() && logo.value != null) {
            error.value = "description field is blank"
        } else if (logo.value == null && name.value.isNotBlank() && description.value.isNotBlank()) {
            error.value = "You have to add a logo"
        } else if (name.value.isNotBlank() && description.value.isNotBlank() && logo.value != null) {
            error.value = "OK"
        } else {
            error.value = "fields are empty"
        }
        return error
    }

    fun createGroup() = viewModelScope.launch {
        val uuid: String = UUID.randomUUID().toString()
        val mImageRef = FirebaseStorage.getInstance().getReference(uuid)

        val uploadLogo = async { mImageRef.putFile(logo.value!!) }
        uploadLogo.await()

        repository.insert(name.value, uuid, description.value)
    }
}