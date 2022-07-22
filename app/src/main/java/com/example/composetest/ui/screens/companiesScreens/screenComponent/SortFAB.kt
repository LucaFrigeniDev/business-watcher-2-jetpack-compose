package com.example.composetest.ui.screens.companiesScreens.screenComponent

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composetest.R
import com.example.composetest.ui.screens.companiesScreens.CompaniesViewModel

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
                            viewModel.sortCompanies(CompaniesViewModel.SortType.TURNOVER)
                            expanded = false
                        }) {
                        Text(
                            stringResource(R.string.sort_turnover),
                            style = MaterialTheme.typography.body1
                        )
                    }

                    DropdownMenuItem({
                        viewModel.sortCompanies(CompaniesViewModel.SortType.GROUP)
                        expanded = false
                    }) {
                        Text(
                            stringResource(R.string.sort_group),
                            style = MaterialTheme.typography.body1
                        )
                    }
                    DropdownMenuItem({
                        viewModel.sortCompanies(CompaniesViewModel.SortType.SECTOR)
                        expanded = false
                    }) {
                        Text(
                            stringResource(R.string.sort_sector),
                            style = MaterialTheme.typography.body1
                        )
                    }
                    DropdownMenuItem({
                        viewModel.sortCompanies(CompaniesViewModel.SortType.DISTANCE)
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