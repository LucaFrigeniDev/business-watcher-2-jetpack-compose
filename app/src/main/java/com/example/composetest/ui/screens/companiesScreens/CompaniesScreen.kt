package com.example.composetest.ui.screens.companiesScreens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.composetest.R
import com.example.composetest.displayScreen
import com.example.composetest.ui.ROUTE_COMPANIES
import com.example.composetest.ui.EmptyScreen
import com.example.composetest.ui.base.screenComponents.SwitchFAB
import com.example.composetest.ui.screens.companiesScreens.screenComponent.CompaniesList
import com.example.composetest.ui.screens.companiesScreens.screenComponent.SortFAB
import com.example.composetest.ui.theme.Grey

@Composable
fun CompaniesScreen(navController: NavController, navBackStackEntry: NavBackStackEntry?) {
    val viewModel = hiltViewModel<CompaniesViewModel>()
    val companies by viewModel.companies.collectAsState(emptyList())
    val businessSectorList by viewModel.businessSectors.collectAsState(emptyList())

    displayScreen = ROUTE_COMPANIES

    if (companies.isNotEmpty())
        CompaniesList(companies, businessSectorList, navController)
    else
        EmptyScreen(stringResource(R.string.empty_company_list), Grey, Modifier.size(250.dp))

    SwitchFAB(navController, navBackStackEntry)
    SortFAB(viewModel)
}