package com.example.composetest.screens.detail.group.ui

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
import com.example.composetest.screens.detail.group.ui.groupScreenComponents.BusinessSectors
import com.example.composetest.screens.detail.group.ui.groupScreenComponents.Companies
import com.example.composetest.screens.detail.headerComponents.DetailScreenHeader
import com.example.composetest.screens.detail.headerComponents.StatRow
import com.example.composetest.theme.standardColumnModifier

@Composable
fun GroupDetailScreen(navController: NavController, id: Long) {
    val viewModel = hiltViewModel<GroupDetailViewModel>()
    viewModel.getGroup(id)

    val groupDetail by viewModel.group.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        DetailScreenHeader(
            navController,
            groupDetail?.group?.logo ?: "",
            groupDetail?.group?.name ?: "",
            null,
            groupDetail?.group?.description ?: "",
            null,
            viewModel
        )

        Spacer(Modifier.padding(10.dp))

        Column(standardColumnModifier) {
            StatRow(
                R.drawable.ic_baseline_tag_24,
                stringResource(R.string.rank, groupDetail?.rank ?: "")
            )
            StatRow(
                R.drawable.ic_baseline_monetization_on_24,
                stringResource(R.string.euro, numberFormat(groupDetail?.turnover?.toInt()))
            )
            StatRow(
                R.drawable.ic_baseline_factory_24,
                stringResource(R.string.group_companies, groupDetail?.numberOfCompanies ?: "")
            )
        }

        Spacer(Modifier.padding(10.dp))

        BusinessSectors(groupDetail?.businessSectorStats ?: emptyList())

        Spacer(Modifier.padding(10.dp))

        Companies(groupDetail?.companies ?: emptyList())

        Spacer(Modifier.padding(10.dp))
    }
}