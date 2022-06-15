package com.example.composetest.ui.screens.detailsScreens

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composetest.R
import com.example.composetest.domain.entities.BusinessSector
import com.example.composetest.domain.entities.Company
import com.example.composetest.domain.other.GroupBusinessSector
import com.example.composetest.domain.relations.BusinessSectorWithCompanies
import com.example.composetest.ui.base.EmptyScreen
import com.example.composetest.ui.screens.companiesScreens.CompanyItemCard
import com.example.composetest.ui.theme.Grey
import com.example.composetest.ui.theme.LightGrey
import com.example.composetest.ui.theme.White

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

        GroupStats(rank, turnover, numberOfCompanies)

        Spacer(Modifier.padding(10.dp))

        BusinessSectors(businessSectors)

        Spacer(Modifier.padding(10.dp))

        Companies(companies)
    }
}

@Composable
fun GroupStats(rank: String, turnover: String, companies: String) = Column(
    Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
) {
    Row {
        Icon(
            painterResource(R.drawable.ic_baseline_tag_24),
            "",
            Modifier.size(30.dp)
        )

        Spacer(Modifier.padding(4.dp))

        Text(
            stringResource(R.string.rank, rank),
            Modifier.padding(top = 4.dp),
            style = MaterialTheme.typography.body1
        )
    }
    Spacer(Modifier.padding(4.dp))
    Row {
        Icon(
            painterResource(R.drawable.ic_baseline_monetization_on_24),
            "",
            Modifier.size(30.dp)
        )
        Spacer(Modifier.padding(4.dp))
        Text(
            stringResource(R.string.euro, turnover),
            Modifier.padding(top = 4.dp),
            style = MaterialTheme.typography.body1
        )
    }
    Spacer(Modifier.padding(4.dp))
    Row {
        Icon(
            painterResource(R.drawable.ic_baseline_factory_24),
            "",
            Modifier.size(30.dp)
        )
        Spacer(Modifier.padding(4.dp))
        Text(
            stringResource(R.string.group_companies, companies),
            Modifier.padding(top = 4.dp),
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun BusinessSectors(businessSectors: List<GroupBusinessSector>) = Column(
    Modifier
        .fillMaxWidth()
        .background(LightGrey)
) {
    Text(
        stringResource(R.string.sector_presence),
        Modifier.padding(16.dp),
        White,
        style = MaterialTheme.typography.subtitle1
    )
    LazyRow {
        items(businessSectors) { businessSector -> BusinessSectorCard(businessSector) }
    }
    Spacer(Modifier.padding(10.dp))
}

@Composable
fun BusinessSectorCard(businessSector: GroupBusinessSector) = Card(
    Modifier
        .padding(6.dp)
        .height(200.dp)
        .width(160.dp),
    RoundedCornerShape(15.dp),
    Color(parseColor(businessSector.color))
) {
    Column(Modifier.padding(8.dp)) {
        Text(
            businessSector.businessSector,
            Modifier.fillMaxWidth(),
            White,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1
        )

        Spacer(Modifier.padding(6.dp))

        Row {
            Icon(
                painterResource(R.drawable.ic_baseline_monetization_on_24),
                "",
                Modifier.size(30.dp),
                White
            )

            Spacer(Modifier.padding(4.dp))

            Text(
                stringResource(R.string.euro, businessSector.turnover),
                Modifier.padding(top = 5.dp),
                White,
                style = MaterialTheme.typography.body2
            )
        }

        Spacer(Modifier.padding(4.dp))

        Row {
            Icon(
                painterResource(R.drawable.ic_baseline_factory_24),
                "",
                Modifier.size(30.dp),
                White
            )

            Spacer(Modifier.padding(4.dp))

            Text(
                stringResource(R.string.group_companies, businessSector.companies),
                Modifier.padding(top = 5.dp),
                White,
                style = MaterialTheme.typography.body2
            )
        }

        Spacer(Modifier.padding(4.dp))

        Row {
            Icon(
                painterResource(R.drawable.ic_baseline_tag_24),
                "",
                Modifier.size(30.dp),
                White
            )

            Spacer(Modifier.padding(4.dp))

            Text(
                stringResource(R.string.rank, businessSector.rank),
                Modifier.padding(top = 5.dp),
                White,
                style = MaterialTheme.typography.body2
            )
        }

        Spacer(Modifier.padding(4.dp))

        Row {
            Icon(
                painterResource(R.drawable.ic_baseline_percent_24),
                "",
                Modifier.size(30.dp),
                White
            )

            Spacer(Modifier.padding(4.dp))

            Text(
                stringResource(R.string.share, businessSector.share),
                Modifier.padding(top = 5.dp),
                White,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

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