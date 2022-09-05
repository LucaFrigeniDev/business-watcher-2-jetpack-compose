package com.example.composetest.screens.detail.company.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.composetest.screens.detail.company.ui.companyScreenComponents.Customers
import com.example.composetest.screens.detail.headerComponents.DetailScreenHeader
import com.example.composetest.screens.detail.headerComponents.StatRow
import com.example.composetest.theme.standardColumnModifier

@Composable
fun CompanyDetailScreen(navController: NavController, id: Long) {
    val viewModel = hiltViewModel<CompanyDetailViewModel>()

    viewModel.getCompany(id)

    val company by viewModel.companyItem.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        DetailScreenHeader(
            navController,
            company?.company?.logo ?: "",
            company?.company?.name ?: "",
            company?.groupName ?: "Independent",
            company?.company?.description ?: "",
            viewModel,
            null
        )

        Spacer(Modifier.padding(2.dp))

        Column(standardColumnModifier) {
            StatRow(R.drawable.ic_baseline_factory_24, company?.businessSectorName ?: "")
            StatRow(
                R.drawable.ic_baseline_monetization_on_24,
                stringResource(
                    R.string.euro, numberFormat(company?.company?.turnover)
                )
            )
            StatRow(
                R.drawable.ic_baseline_location_on_24,
                company?.company?.address + ","
                        + company?.company?.city + ","
                        + company?.company?.postalCode
            )
        }

        Spacer(Modifier.padding(2.dp))

        Customers(company?.company?.customers ?: emptyList(), viewModel)
    }
}