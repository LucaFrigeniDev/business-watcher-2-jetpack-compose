package com.example.composetest.screens.lists.business_sector_list.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.composetest.base.BackBtn
import com.example.composetest.base.EmptyScreen
import com.example.composetest.R
import com.example.composetest.base.Title
import com.example.composetest.screens.lists.business_sector_list.ui.screen_components.AddBusinessSectorDialog
import com.example.composetest.screens.lists.business_sector_list.ui.screen_components.BusinessSectorList
import com.example.composetest.theme.Grey
import com.example.composetest.theme.standardColumnModifier

@Composable
fun BusinessSectorScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<BusinessSectorViewModel>()
    val scrollState = rememberScrollState()

    val isBusinessSectorListEmpty by viewModel.isBusinessSectorDBEmpty.collectAsState()
    val isBusinessSectorListFull by viewModel.isBusinessSectorDBFull.collectAsState()
    val businessSectors by viewModel.businessSectorsWithCompanies.collectAsState()

    Column(standardColumnModifier.verticalScroll(scrollState)) {
        BackBtn(navController)
        Title(stringResource(R.string.sector))

        if (isBusinessSectorListEmpty)
            EmptyScreen(
                stringResource(R.string.empty_sector_list),
                Grey,
                Modifier.size(250.dp)
            )
        else
            BusinessSectorList(businessSectors)
    }

    if (!isBusinessSectorListFull)
        AddBusinessSectorFAB(viewModel)
}

@Composable
fun AddBusinessSectorFAB(viewModel: BusinessSectorViewModel) {
    val isDialogOpen = rememberSaveable { mutableStateOf(false) }

    Column(
        standardColumnModifier,
        Arrangement.Bottom,
        Alignment.End
    ) {
        FloatingActionButton({ isDialogOpen.value = true }) {
            Icon(Icons.Default.Add, "")
        }
    }

    if (isDialogOpen.value)
        AddBusinessSectorDialog(isDialogOpen, viewModel)
}

