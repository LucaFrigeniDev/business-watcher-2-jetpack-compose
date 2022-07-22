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
import com.example.composetest.ui.screens.detailsScreens.groupScreenComponents.BusinessSectors
import com.example.composetest.ui.screens.detailsScreens.groupScreenComponents.Companies
import com.example.composetest.ui.screens.detailsScreens.headerComponents.DetailScreenHeader
import com.example.composetest.ui.screens.detailsScreens.headerComponents.StatRow
import com.example.composetest.ui.theme.standardColumnModifier

@Composable
fun GroupDetailScreen(navController: NavController, id: Long) {
    val viewModel = hiltViewModel<GroupDetailViewModel>()
    viewModel.getGroup(id)

    val group by viewModel.group.collectAsState()
    val rank by viewModel.rank.collectAsState()
    val turnover by viewModel.turnover.collectAsState()
    val numberOfCompanies by viewModel.numberOfCompanies.collectAsState()
    val businessSectors by viewModel.businessSectors.collectAsState()
    val companies by viewModel.companies.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        DetailScreenHeader(
            navController,
            group?.logo ?: "",
            group?.name ?: "",
            null,
            group?.description ?: "",
            null,
            viewModel
        )

        Spacer(Modifier.padding(10.dp))

        Column(standardColumnModifier) {
            StatRow(R.drawable.ic_baseline_tag_24, stringResource(R.string.rank, rank))
            StatRow(R.drawable.ic_baseline_monetization_on_24, stringResource(R.string.euro, turnover))
            StatRow(R.drawable.ic_baseline_factory_24, stringResource(R.string.group_companies, numberOfCompanies))
        }

        Spacer(Modifier.padding(10.dp))

        BusinessSectors(businessSectors)

        Spacer(Modifier.padding(10.dp))

        Companies(companies)

        Spacer(Modifier.padding(10.dp))
    }
}