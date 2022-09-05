package com.example.composetest.screens.detail.group.ui.groupScreenComponents

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composetest.base.EmptyScreen
import com.example.composetest.R
import com.example.composetest.screens.lists.company_list.domain.CompanyItem
import com.example.composetest.screens.lists.company_list.ui.screenComponent.CompanyItemCard
import com.example.composetest.theme.Grey

@Composable
fun Companies(companyItemList: List<CompanyItem>) {
    Text(
        stringResource(R.string.companies_title),
        Modifier.padding(16.dp),
        color = Grey,
        style = MaterialTheme.typography.subtitle1
    )

    if (companyItemList.isEmpty())
        EmptyScreen(stringResource(R.string.empty_company_list), Grey, Modifier.size(200.dp))
    else {
        companyItemList.forEach {
            CompanyItemCard(it.company, it.businessSector.color, null)
        }

        Spacer(Modifier.size(10.dp))
    }
}