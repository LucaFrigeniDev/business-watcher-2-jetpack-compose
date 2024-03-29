package com.example.composetest.screens.creation.group.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.composetest.R
import com.example.composetest.base.BackBtn
import com.example.composetest.base.Title
import com.example.composetest.screens.creation.screenComponents.CreationTextField
import com.example.composetest.screens.creation.screenComponents.GroupLogoSection
import com.example.composetest.screens.creation.screenComponents.ValidationFAB
import com.example.composetest.theme.modifierFullLength
import com.example.composetest.theme.modifierHeight
import com.example.composetest.theme.standardColumnModifier

@Composable
fun GroupCreationScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<GroupCreationViewModel>()
    val scrollState = rememberScrollState()

    var name by rememberSaveable { mutableStateOf("") }
    val nameUpdate = { viewModel.setName(name) }

    var description by rememberSaveable { mutableStateOf("") }
    val descriptionUpdate = { viewModel.setDescription(description) }

    Column(standardColumnModifier.verticalScroll(scrollState)) {
        BackBtn(navController)
        Title(stringResource(R.string.create_group_title))

        CreationTextField(
            stringResource(R.string.name),
            name,
            { name = it },
            nameUpdate,
            25,
            true,
            modifierFullLength,
            false
        )

        CreationTextField(
            stringResource(R.string.description),
            description,
            { description = it },
            descriptionUpdate,
            250,
            false,
            modifierHeight,
            false
        )

        GroupLogoSection(viewModel)
    }

    ValidationFAB(null, viewModel, navController)
}