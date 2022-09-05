package com.example.composetest.screens.creation.company.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.composetest.R
import com.example.composetest.screens.creation.screenComponents.CompanyLogoSection
import com.example.composetest.screens.creation.screenComponents.CreationDropDownMenu
import com.example.composetest.screens.creation.screenComponents.CreationTextField
import com.example.composetest.screens.creation.screenComponents.ValidationFAB
import com.example.composetest.theme.modifierFullLength
import com.example.composetest.theme.modifierHeight
import com.example.composetest.theme.modifierSmallLength
import com.example.composetest.base.BackBtn
import com.example.composetest.base.Title

@Composable
fun CompanyCreationScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<CompanyCreationViewModel>()
    val scrollState = rememberScrollState()

    val groups: List<String> by viewModel.groupsNames.collectAsState()
    var selectedGroup by rememberSaveable { mutableStateOf("") }
    val setGroup = { viewModel.setGroup(selectedGroup) }

    val sectors: List<String> by viewModel.sectorsNames.collectAsState()
    var selectedSector by rememberSaveable { mutableStateOf("") }
    val setSector = { viewModel.setSector(selectedSector) }

    var name by rememberSaveable { mutableStateOf("") }
    val nameUpdater = { viewModel.setName(name) }

    var postalCode by rememberSaveable { mutableStateOf("") }
    val postalCodeUpdater = { viewModel.setPostalCode(postalCode) }

    var city by rememberSaveable { mutableStateOf("") }
    val cityUpdater = { viewModel.setCity(city) }

    var address by rememberSaveable { mutableStateOf("") }
    val addressUpdater = { viewModel.setAddress(address) }

    var turnover by rememberSaveable { mutableStateOf("") }
    val turnoverUpdater = { viewModel.setTurnover(turnover.toInt()) }

    var description by rememberSaveable { mutableStateOf("") }
    val descriptionUpdater = { viewModel.setDescription(description) }

    Column(
        Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        BackBtn(navController)
        Title(stringResource(R.string.create_company_title))

        CreationTextField(
            stringResource(R.string.name),
            name,
            { name = it },
            nameUpdater,
            25,
            true,
            modifierFullLength,
            false
        )

        CreationDropDownMenu(
            stringResource(R.string.sector),
            sectors,
            selectedSector,
            { selectedSector = it },
            setSector
        )

        CreationDropDownMenu(
            stringResource(R.string.group),
            groups,
            selectedGroup,
            { selectedGroup = it },
            setGroup
        )

        CompanyLogoSection(viewModel)

        Row(Modifier.fillMaxWidth(), Arrangement.End) {
            CreationTextField(
                stringResource(R.string.postal_code),
                postalCode,
                { postalCode = it },
                postalCodeUpdater,
                10,
                true,
                modifierSmallLength,
                false
            )

            Spacer(Modifier.padding(2.dp))

            CreationTextField(
                stringResource(R.string.city),
                city,
                { city = it },
                cityUpdater,
                15,
                true,
                modifierFullLength,
                false
            )
        }

        CreationTextField(
            stringResource(R.string.street),
            address,
            { address = it },
            addressUpdater,
            30,
            true,
            modifierFullLength,
            false
        )

        Spacer(Modifier.padding(8.dp))

        CreationTextField(
            stringResource(R.string.turnover),
            turnover,
            { turnover = it },
            turnoverUpdater,
            15,
            true,
            modifierFullLength,
            true
        )

        CreationTextField(
            stringResource(R.string.description),
            description,
            { description = it },
            descriptionUpdater,
            250,
            false,
            modifierHeight,
            false
        )

        Spacer(Modifier.padding(40.dp))
    }
    ValidationFAB(viewModel, null, navController)
}