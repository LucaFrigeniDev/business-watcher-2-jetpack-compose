package com.example.composetest.screens.lists.company_list.ui

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.composetest.base.EmptyScreen
import com.example.composetest.R
import com.example.composetest.base.ROUTE_COMPANIES
import com.example.composetest.base.screenComponents.SwitchFAB
import com.example.composetest.displayScreen
import com.example.composetest.screens.lists.company_list.ui.screenComponent.CompaniesList
import com.example.composetest.screens.lists.company_list.ui.screenComponent.SortFAB
import com.example.composetest.theme.Grey

@Composable
fun CompaniesScreen(navController: NavController, navBackStackEntry: NavBackStackEntry?) {
    val viewModel = hiltViewModel<CompaniesViewModel>()
    val companyItemList by viewModel.companyItemList.collectAsState()

    displayScreen = ROUTE_COMPANIES

    if (companyItemList.isNotEmpty())
        CompaniesList(companyItemList, navController)
    else
        EmptyScreen(stringResource(R.string.empty_company_list), Grey, Modifier.size(250.dp))

    SwitchFAB(navController, navBackStackEntry)
    SortFAB(viewModel)
}