package com.example.composetest.ui.screens.groupsScreen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.composetest.R
import com.example.composetest.displayScreen
import com.example.composetest.ui.ROUTE_GROUPS
import com.example.composetest.ui.EmptyScreen
import com.example.composetest.ui.base.screenComponents.SwitchFAB
import com.example.composetest.ui.screens.groupsScreen.screenComponent.GroupsList
import com.example.composetest.ui.theme.Grey

@Composable
fun GroupsScreen(navController: NavController, navBackStackEntry: NavBackStackEntry?) {
    val viewModel = hiltViewModel<GroupsViewModel>()
    val groups by viewModel.groups.collectAsState()

    displayScreen = ROUTE_GROUPS

    if (groups.isNotEmpty()) GroupsList(navController, groups)
    else EmptyScreen(stringResource(R.string.empty_group_list), Grey, Modifier.size(250.dp))

    SwitchFAB(navController, navBackStackEntry)
}