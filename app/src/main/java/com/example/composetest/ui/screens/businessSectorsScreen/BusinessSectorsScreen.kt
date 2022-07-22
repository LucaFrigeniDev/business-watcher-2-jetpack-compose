package com.example.composetest.ui.screens.businessSectorsScreen

import androidx.compose.foundation.layout.*
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
import com.example.composetest.R
import com.example.composetest.ui.BackBtn
import com.example.composetest.ui.EmptyScreen
import com.example.composetest.ui.Title
import com.example.composetest.ui.screens.businessSectorsScreen.screenComponents.AddBusinessSectorDialog
import com.example.composetest.ui.screens.businessSectorsScreen.screenComponents.BusinessSectorList
import com.example.composetest.ui.theme.Grey
import com.example.composetest.ui.theme.standardColumnModifier

@Composable
fun BusinessSectorScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<BusinessSectorViewModel>()
    val scrollState = rememberScrollState()

    val listSize by viewModel.businessSectorListSize.collectAsState(0)
    val businessSectors by viewModel.businessSectors().collectAsState(emptyList())

    Column(standardColumnModifier.verticalScroll(scrollState)) {
        BackBtn(navController)
        Title(stringResource(R.string.sector))

        if (listSize == 0)
            EmptyScreen(
                stringResource(R.string.empty_sector_list),
                Grey,
                Modifier.size(250.dp)
            )
        else
            BusinessSectorList(businessSectors)
    }

    if (listSize < 5)
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

