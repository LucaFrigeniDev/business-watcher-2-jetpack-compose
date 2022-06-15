package com.example.composetest.ui.base

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composetest.ui.*
import com.example.composetest.ui.theme.DarkGrey
import com.example.composetest.ui.theme.White

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BaseScreen() {
    val bottomDrawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val startDrawerState = rememberScaffoldState(DrawerState(DrawerValue.Closed))

    val navController = rememberNavController()
    var isFullScreen by rememberSaveable { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    isFullScreen = when (navBackStackEntry?.destination?.route) {
        ROUTE_BUSINESS_SECTORS -> true
        ROUTE_COMPANY -> true
        ROUTE_GROUP -> true
        ROUTE_CREATION_COMPANY -> true
        ROUTE_CREATION_GROUP -> true
        else -> false
    }

    BottomDrawer(
        {
            BottomSheet(bottomDrawerState, navController, navBackStackEntry)
        },
        drawerState = bottomDrawerState,
        gesturesEnabled = false
    ) {
        Scaffold(
            scaffoldState = startDrawerState,
            topBar = { if (!isFullScreen) Tabs(navController, navBackStackEntry) },
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = { if (!isFullScreen) AddFAB(navController) },
            isFloatingActionButtonDocked = true,
            bottomBar = {
                if (!isFullScreen) BottomAppBar(
                    bottomDrawerState,
                    startDrawerState
                )
            },
            drawerContent = { StartDrawer(startDrawerState, navController) },
            drawerBackgroundColor = DarkGrey,
            drawerContentColor = White,
            drawerGesturesEnabled = false
        ) {

            Box(Modifier.fillMaxSize()) {
                Navigation(navController, navBackStackEntry)
                if (!isFullScreen) SearchBar(navController, navBackStackEntry)
            }
        }
    }
}
