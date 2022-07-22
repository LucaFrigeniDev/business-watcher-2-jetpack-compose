package com.example.composetest.ui.screens.detailsScreens.groupScreenComponents

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composetest.R
import com.example.composetest.domain.entities.BusinessSector
import com.example.composetest.domain.entities.Company
import com.example.composetest.domain.relations.BusinessSectorWithCompanies
import com.example.composetest.ui.EmptyScreen
import com.example.composetest.ui.screens.companiesScreens.screenComponent.CompanyItemCard
import com.example.composetest.ui.theme.Grey

@Composable
fun Companies(it: List<BusinessSectorWithCompanies>) {
    val companies: MutableList<Company> = mutableListOf()
    val businessSectors: MutableList<BusinessSector> = mutableListOf()

    for (businessSectorWithCompanies in it) {
        businessSectors.add(businessSectorWithCompanies.businessSector)
        companies.addAll(businessSectorWithCompanies.companies)
    }

    companies.sortedWith(
        compareBy<Company> { company -> company.businessSectorId }
            .thenByDescending { company -> company.turnover }
    )

    Text(
        stringResource(R.string.companies_title),
        Modifier.padding(16.dp),
        color = Grey,
        style = MaterialTheme.typography.subtitle1
    )

    if (companies.isEmpty())
        EmptyScreen(stringResource(R.string.empty_company_list), Grey, Modifier.size(200.dp))
    else {
        for (company in companies) CompanyItemCard(company, businessSectors, null)
        Spacer(Modifier.size(10.dp))
    }
}