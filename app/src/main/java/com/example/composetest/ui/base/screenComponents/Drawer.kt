package com.example.composetest.ui.base.screenComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composetest.R
import com.example.composetest.ui.ROUTE_BUSINESS_SECTORS
import com.example.composetest.ui.theme.DarkGrey
import com.example.composetest.ui.theme.LightWhite
import com.example.composetest.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun StartDrawer(startDrawerState: ScaffoldState, navController: NavController) {
    val scope = rememberCoroutineScope()

    Column {
        Box(
            Modifier
                .background(LightWhite)
                .fillMaxWidth()
                .height(100.dp),
            Alignment.TopEnd
        ) {
            Text(
                stringResource(R.string.app_name),
                Modifier
                    .fillMaxSize()
                    .padding(top = 25.dp),
                DarkGrey,
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center
            )

            FloatingActionButton(
                { scope.launch { startDrawerState.drawerState.close() } },
                Modifier
                    .size(40.dp)
                    .padding(top = 10.dp, end = 10.dp),
                backgroundColor = DarkGrey
            ) {
                Icon(
                    Icons.Default.KeyboardArrowLeft,
                    "",
                    Modifier.size(18.dp),
                    White
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        DropdownMenuItem({
            scope.launch {
                startDrawerState.drawerState.close()
                navController.navigate(ROUTE_BUSINESS_SECTORS)
            }
        }) {
            Text(stringResource(R.string.sector), style = MaterialTheme.typography.body1)
        }

        DropdownMenuItem({
            scope.launch { startDrawerState.drawerState.close() }
        }) {
            Text(stringResource(R.string.currency), style = MaterialTheme.typography.body1)
        }

        DropdownMenuItem({
            scope.launch { startDrawerState.drawerState.close() }
        }) {
            Text(stringResource(R.string.export_Json), style = MaterialTheme.typography.body1)
        }

        DropdownMenuItem({
            scope.launch { startDrawerState.drawerState.close() }
        }) {
            Text(stringResource(R.string.import_Json), style = MaterialTheme.typography.body1)
        }
    }
}