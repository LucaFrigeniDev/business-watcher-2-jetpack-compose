package com.example.composetest.screens.creation.group.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.screens.creation.group.domain.CreateGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupCreationViewModel
@Inject constructor(
    private val createGroupUseCase: CreateGroupUseCase
) : ViewModel() {

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
        createGroupUseCase.execute(name.value, logo.value!!, description.value)
    }
}