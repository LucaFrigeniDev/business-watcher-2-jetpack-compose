package com.example.composetest.ui.screens.companiesScreens

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.composetest.R
import com.example.composetest.domain.entities.BusinessSector
import com.example.composetest.domain.entities.Company
import com.example.composetest.setPhotoUrl
import com.example.composetest.ui.ROUTE_COMPANIES
import com.example.composetest.ui.ROUTE_GROUPS
import com.example.composetest.ui.base.EmptyScreen
import com.example.composetest.ui.screens.companiesScreens.CompaniesViewModel.SortType
import com.example.composetest.ui.theme.Grey
import com.example.composetest.ui.theme.White

@Composable
fun CompaniesScreen(navController: NavController, navBackStackEntry: NavBackStackEntry?) {
    val viewModel = hiltViewModel<CompaniesViewModel>()
    val companies by viewModel.companies.collectAsState(emptyList())
    val businessSectorList by viewModel.businessSectors.collectAsState(emptyList())

    if (companies.isNotEmpty())
        CompaniesList(companies, businessSectorList, navController)
    else
        EmptyScreen(stringResource(R.string.empty_company_list), Grey, Modifier.size(250.dp))

    ChangeFAB(navController, navBackStackEntry)
    SortFAB(viewModel)
}

@Composable
fun CompaniesList(
    companies: List<Company>,
    businessSectors: List<BusinessSector>,
    navController: NavController
) = LazyColumn(contentPadding = PaddingValues(vertical = 100.dp, horizontal = 18.dp)) {
    items(companies) { company ->
        CompanyItemCard(company, businessSectors, navController)
    }
}

@Composable
fun CompanyItemCard(
    company: Company,
    businessSectors: List<BusinessSector>,
    navController: NavController?
) {
    var color = ""

    for (businessSector in businessSectors) {
        if (businessSector.id == company.businessSectorId)
            color = businessSector.color
    }

    Box(
        Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .height(100.dp)
            .clickable { navController?.navigate("company?id=${company.id}") },
    ) {
        Card(
            Modifier
                .fillMaxSize()
                .padding(start = 50.dp, top = 6.dp, bottom = 6.dp),
            RoundedCornerShape(20),
            Grey

        ) {
            Column(Modifier.padding(start = 70.dp, top = 4.dp)) {
                Text(
                    company.name,
                    Modifier.weight(1F),
                    White,
                    maxLines = 1,
                    style = MaterialTheme.typography.subtitle1
                )

                Text(
                    stringResource(R.string.euro, company.turnover.toString()),
                    Modifier.weight(1F),
                    White,
                    maxLines = 1,
                    style = MaterialTheme.typography.body2
                )

                Text(
                    company.city,
                    Modifier.weight(1F),
                    White,
                    maxLines = 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }

        AsyncImage(
            setPhotoUrl(company.logo),
            "",
            Modifier
                .background(White, RoundedCornerShape(50))
                .border(6.dp, Color(parseColor(color)), RoundedCornerShape(50))
                .size(100.dp)
                .clip(RoundedCornerShape(50)),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun SortFAB(viewModel: CompaniesViewModel) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        Modifier.fillMaxSize(),
        Arrangement.Bottom,
        Alignment.CenterHorizontally
    ) {
        Row(horizontalArrangement = Arrangement.Center) {
            Spacer(Modifier.padding(start = 90.dp))
            Box {
                FloatingActionButton(
                    { expanded = true },
                    Modifier.size(40.dp)
                ) {
                    Icon(painterResource(R.drawable.ic_baseline_sort_24), "")
                }

                DropdownMenu(expanded, { expanded = false }) {
                    DropdownMenuItem(
                        {
                            viewModel.sortCompanies(SortType.TURNOVER)
                            expanded = false
                        }) {
                        Text(
                            stringResource(R.string.sort_turnover),
                            style = MaterialTheme.typography.body1
                        )
                    }

                    DropdownMenuItem({
                        viewModel.sortCompanies(SortType.GROUP)
                        expanded = false
                    }) {
                        Text(
                            stringResource(R.string.sort_group),
                            style = MaterialTheme.typography.body1
                        )
                    }
                    DropdownMenuItem({
                        viewModel.sortCompanies(SortType.SECTOR)
                        expanded = false
                    }) {
                        Text(
                            stringResource(R.string.sort_sector),
                            style = MaterialTheme.typography.body1
                        )
                    }
                    DropdownMenuItem({
                        viewModel.sortCompanies(SortType.DISTANCE)
                        expanded = false
                    }) {
                        Text(
                            stringResource(R.string.sort_nearest),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
        Spacer(Modifier.padding(bottom = 65.dp))
    }
}

@Composable
fun ChangeFAB(navController: NavController, navBackStackEntry: NavBackStackEntry?) =
    Column(
        Modifier.fillMaxSize(),
        Arrangement.Bottom,
        Alignment.CenterHorizontally
    ) {
        FloatingActionButton(
            {
                if (navBackStackEntry?.destination?.route == ROUTE_COMPANIES) {
                    navController.navigate(ROUTE_GROUPS)
                } else navController.navigate(ROUTE_COMPANIES)
            },
            Modifier.size(40.dp),
        ) {
            Icon(painterResource(R.drawable.ic_baseline_change_circle_24), "")
        }
        Spacer(Modifier.padding(bottom = 90.dp))
    }

