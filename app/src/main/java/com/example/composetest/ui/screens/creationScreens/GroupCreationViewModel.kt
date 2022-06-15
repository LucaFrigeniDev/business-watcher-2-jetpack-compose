package com.example.composetest.ui.screens.creationScreens

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.data.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupCreationViewModel
@Inject constructor(private val repository: GroupRepository) : ViewModel() {

    private val name = MutableStateFlow("")
    private val description = MutableStateFlow("")
    private val logoUri = MutableStateFlow<Uri?>(null)

    fun setName(groupName: String) {
        name.value = groupName
    }

    fun setDescription(groupDescription: String) {
        description.value = groupDescription
    }

    fun setLogo(logo: Uri?) {
        logoUri.value = logo
    }

    fun isCorrectlyField(): StateFlow<String> {
        val error = MutableStateFlow("")
        if (name.value.isBlank() && description.value.isNotBlank() && logoUri.value != null) {
            error.value = "name field is blank"
        } else if (description.value.isBlank() && name.value.isNotBlank() && logoUri.value != null) {
            error.value = "description field is blank"
        } else if (logoUri.value == null && name.value.isNotBlank() && description.value.isNotBlank()) {
            error.value = "You have to add a logo"
        } else if (name.value.isNotBlank() && description.value.isNotBlank() && logoUri.value != null) {
            error.value = "OK"
        } else {
            error.value = "fields are empty"
        }
        return error
    }

    fun insert() = viewModelScope.launch {
        repository.insert(name.value, logoUri.value, description.value)
    }
}