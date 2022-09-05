package com.example.composetest.base.screenComponents

import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.composetest.R
import com.example.composetest.base.ROUTE_COMPANIES
import com.example.composetest.base.ROUTE_GROUPS

@Composable
fun SwitchFAB(navController: NavController, navBackStackEntry: NavBackStackEntry?) =
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