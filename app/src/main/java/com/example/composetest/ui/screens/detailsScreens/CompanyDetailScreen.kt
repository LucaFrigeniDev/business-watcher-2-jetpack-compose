package com.example.composetest.ui.screens.detailsScreens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composetest.R
import com.example.composetest.numberFormat
import com.example.composetest.ui.screens.detailsScreens.companyScreenComponents.Customers
import com.example.composetest.ui.screens.detailsScreens.headerComponents.DetailScreenHeader
import com.example.composetest.ui.screens.detailsScreens.headerComponents.StatRow
import com.example.composetest.ui.theme.standardColumnModifier

@Composable
fun CompanyDetailScreen(navController: NavController, id: Long) {
    val viewModel = hiltViewModel<CompanyDetailViewModel>()
    viewModel.getCompany(id)

    val company by viewModel.company.collectAsState()
    val businessSectorName by viewModel.businessSectorName.collectAsState()
    val groupName by viewModel.groupName.collectAsState()
    val turnover = numberFormat(company?.turnover)
    val address = company?.address + ", " + company?.city + ", " + company?.postalCode

    val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        DetailScreenHeader(
            navController,
            company?.logo ?: "",
            company?.name ?: "",
            groupName,
            company?.description ?: "",
            viewModel,
            null
        )

        Spacer(Modifier.padding(2.dp))

        Column(standardColumnModifier) {
            StatRow(R.drawable.ic_baseline_factory_24, businessSectorName)
            StatRow(R.drawable.ic_baseline_monetization_on_24, stringResource(R.string.euro, turnover))
            StatRow(R.drawable.ic_baseline_location_on_24, address)
        }

        Spacer(Modifier.padding(2.dp))

        Customers(company?.customers ?: emptyList(), viewModel)
    }
}