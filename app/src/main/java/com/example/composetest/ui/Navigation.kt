package com.example.composetest.ui

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.composetest.ui.screens.businessSectorsScreen.BusinessSectorScreen
import com.example.composetest.ui.screens.companiesScreens.CompaniesScreen
import com.example.composetest.ui.screens.companiesScreens.MapScreen
import com.example.composetest.ui.screens.creationScreens.CompanyCreationScreen
import com.example.composetest.ui.screens.creationScreens.GroupCreationScreen
import com.example.composetest.ui.screens.detailsScreens.CompanyDetailScreen
import com.example.composetest.ui.screens.detailsScreens.GroupDetailScreen
import com.example.composetest.ui.screens.groupsScreen.GroupsScreen
import com.example.composetest.ui.theme.LightWhite

@Composable
fun Navigation(navController: NavHostController, navBackStackEntry: NavBackStackEntry?) {
    NavHost(navController, ROUTE_MAP, Modifier.background(LightWhite)) {
        composable(ROUTE_MAP) { MapScreen(navController) }
        composable(ROUTE_COMPANIES) { CompaniesScreen(navController, navBackStackEntry) }
        composable(ROUTE_GROUPS) { GroupsScreen(navController, navBackStackEntry) }
        composable(ROUTE_BUSINESS_SECTORS) { BusinessSectorScreen(navController) }
        composable(ROUTE_CREATION_COMPANY) { CompanyCreationScreen(navController) }
        composable(ROUTE_CREATION_GROUP) { GroupCreationScreen(navController) }
        composable(
            ROUTE_COMPANY,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { CompanyDetailScreen(navController, it.arguments!!.getLong("id")) }
        composable(
            ROUTE_GROUP,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { GroupDetailScreen(navController, it.arguments!!.getLong("id")) }
    }
}

const val ROUTE_MAP = "map"
const val ROUTE_COMPANIES = "companies"
const val ROUTE_GROUPS = "groups"
const val ROUTE_BUSINESS_SECTORS = "sector"
const val ROUTE_CREATION_COMPANY = "create_company"
const val ROUTE_CREATION_GROUP = "create_group"
const val ROUTE_COMPANY = "company?id={id}"
const val ROUTE_GROUP = "group?id={id}"