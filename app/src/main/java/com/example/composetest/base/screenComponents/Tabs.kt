package com.example.composetest.base.screenComponents

import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.composetest.R
import com.example.composetest.base.ROUTE_COMPANIES
import com.example.composetest.base.ROUTE_GROUPS
import com.example.composetest.base.ROUTE_MAP
import com.example.composetest.theme.DarkGrey
import com.example.composetest.theme.LightGrey
import com.example.composetest.theme.LightWhite

@Composable
fun Tabs(navController: NavController, navBackStackEntry: NavBackStackEntry?) {

    var selectedTab by rememberSaveable { mutableStateOf(0) }

    when (navBackStackEntry?.destination?.route) {
        ROUTE_MAP -> selectedTab = 0
        ROUTE_COMPANIES -> selectedTab = 1
        ROUTE_GROUPS -> selectedTab = 1
    }


    TabRow(selectedTab, backgroundColor = LightWhite) {
        Tab(
            selectedTab == 0,
            {
                if (selectedTab != 0) {
                    selectedTab = 0
                    navController.navigate(ROUTE_MAP)
                }
            },
            icon = {
                val color =
                    if (selectedTab == 0) DarkGrey
                    else LightGrey

                Icon(
                    painterResource(R.drawable.ic_baseline_map_24),
                    "",
                    tint = color
                )
            }
        )

        Tab(
            selectedTab == 1,
            {
                if (selectedTab != 1) {
                    selectedTab = 1
                    navController.navigate(ROUTE_COMPANIES)
                }
            },
            icon = {
                val color =
                    if (selectedTab == 1) DarkGrey
                    else LightGrey

                Icon(
                    painterResource(R.drawable.ic_baseline_list_24),
                    "",
                    tint = color
                )
            }
        )
    }
}
