package com.example.composetest.ui.base.screenComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composetest.R
import com.example.composetest.toast
import com.example.composetest.ui.ROUTE_CREATION_COMPANY
import com.example.composetest.ui.ROUTE_CREATION_GROUP
import com.example.composetest.ui.screens.businessSectorsScreen.BusinessSectorViewModel
import com.example.composetest.ui.theme.DarkGrey
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomAppBar(
    bottomDrawerState: BottomDrawerState,
    startDrawerState: ScaffoldState
) {
    val scope = rememberCoroutineScope()

    BottomAppBar(cutoutShape = CircleShape) {
        IconButton(
            { scope.launch { startDrawerState.drawerState.open() } }
        ) {
            Icon(Icons.Default.Menu, "", tint = DarkGrey)
        }

        Spacer(Modifier.weight(1F, true))

        IconButton(
            { scope.launch { bottomDrawerState.expand() } }
        ) {
            Icon(Icons.Default.Search, "", tint = DarkGrey)
        }
    }
}

@Composable
fun AddFAB(navController: NavController) {
    val viewModel = hiltViewModel<BusinessSectorViewModel>()
    val size by viewModel.businessSectorListSize.collectAsState(0)
    var expanded by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Box {
        FloatingActionButton({
            if (size != 0)
                expanded = true
            else
                context.toast(R.string.error_no_sector)
        }) {
            Icon(Icons.Default.Add, "")
        }

        DropdownMenu(expanded, { expanded = false }) {
            DropdownMenuItem({
                expanded = false
                navController.navigate(ROUTE_CREATION_GROUP) {
                    popUpTo(ROUTE_CREATION_GROUP) { inclusive }
                }

            }) {
                Text(
                    stringResource(R.string.add_group),
                    style = MaterialTheme.typography.body1
                )
            }
            DropdownMenuItem({
                expanded = false
                navController.navigate(ROUTE_CREATION_COMPANY) {
                    popUpTo(ROUTE_CREATION_COMPANY) { inclusive }
                }

            }) {
                Text(
                    stringResource(R.string.add_company),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}