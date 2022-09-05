package com.example.composetest.base.screenComponents

import android.graphics.Color.parseColor
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.composetest.R
import com.example.composetest.base.BottomSheetFiltersViewModel
import com.example.composetest.base.ROUTE_COMPANIES
import com.example.composetest.base.ROUTE_GROUPS
import com.example.composetest.base.ROUTE_MAP
import com.example.composetest.screens.lists.business_sector_list.data.BusinessSector
import com.example.composetest.screens.lists.group_list.data.Group
import com.example.composetest.theme.DarkGrey
import com.example.composetest.theme.DarkWhite
import com.example.composetest.theme.Grey
import com.example.composetest.theme.White
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.launch

var filteredBusinessSectors = mutableListOf<Long>()
var filteredGroups = mutableListOf<Long>()
var isIndependentChipChecked = true

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    bottomDrawerState: BottomDrawerState,
    navController: NavController,
    navBackStackEntry: NavBackStackEntry?
) {
    val viewModel = hiltViewModel<BottomSheetFiltersViewModel>()
    val groups by viewModel.groups.collectAsState(emptyList())
    val businessSectors by viewModel.businessSectors.collectAsState(emptyList())

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            { scope.launch { bottomDrawerState.close() } },
            Modifier.size(40.dp),
            backgroundColor = DarkGrey
        ) { Icon(Icons.Default.KeyboardArrowDown, "", tint = White) }

        Text(
            stringResource(R.string.filter_title),
            Modifier.fillMaxWidth(), DarkGrey, textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h2
        )

        Spacer(Modifier.size(36.dp))

        BusinessSectorsSection(businessSectors, navController, navBackStackEntry)

        Spacer(Modifier.size(30.dp))

        GroupsSection(groups, navController, navBackStackEntry)
    }
}

@Composable
fun BusinessSectorsSection(
    businessSectors: List<BusinessSector>,
    navController: NavController,
    navBackStackEntry: NavBackStackEntry?
) {
    var isAllSelected: Boolean? by rememberSaveable { mutableStateOf(true) }
    var isNothingSelected: Boolean? by rememberSaveable { mutableStateOf(false) }

    Text(
        stringResource(R.string.sectors_chips_title),
        Modifier.fillMaxWidth(),
        color = Grey,
        style = MaterialTheme.typography.subtitle1
    )

    SelectionButtons(
        { isAllSelected = it },
        { isNothingSelected = it },
        navController,
        navBackStackEntry
    )

    BusinessSectorChipGroup(
        businessSectors,
        isAllSelected,
        { isAllSelected = it },
        isNothingSelected,
        { isNothingSelected = it },
        navController,
        navBackStackEntry
    )
}

@Composable
fun GroupsSection(
    groups: List<Group>,
    navController: NavController,
    navBackStackEntry: NavBackStackEntry?
) {
    var isAllSelected: Boolean? by rememberSaveable { mutableStateOf(true) }
    var isNothingSelected: Boolean? by rememberSaveable { mutableStateOf(false) }

    Text(
        stringResource(R.string.groups_chips_title),
        Modifier.fillMaxWidth(),
        color = Grey,
        style = MaterialTheme.typography.subtitle1
    )

    SelectionButtons(
        { isAllSelected = it },
        { isNothingSelected = it },
        navController,
        navBackStackEntry
    )

    GroupChipGroup(
        groups, isAllSelected,
        { isAllSelected = it },
        isNothingSelected,
        { isNothingSelected = it },
        navController,
        navBackStackEntry
    )
}

@Composable
fun SelectionButtons(
    isAllSelectedStateChange: (Boolean?) -> Unit,
    isNothingSelectedStateChange: (Boolean?) -> Unit,
    navController: NavController,
    navBackStackEntry: NavBackStackEntry?
) {
    Row(Modifier.fillMaxWidth(), Arrangement.Center) {
        TextButton(
            {
                isAllSelectedStateChange(true)
                isNothingSelectedStateChange(false)
                when (navBackStackEntry?.destination?.route) {
                    ROUTE_MAP -> navController.navigate(ROUTE_MAP)
                    ROUTE_COMPANIES -> navController.navigate(ROUTE_COMPANIES)
                    ROUTE_GROUPS -> navController.navigate(ROUTE_GROUPS)
                }
            },
            Modifier.weight(10F),
        ) {
            Text(
                stringResource(R.string.select_all),
                color = DarkGrey,
                style = MaterialTheme.typography.button
            )
        }

        Spacer(Modifier.weight(1F))

        TextButton(
            {
                isNothingSelectedStateChange(true)
                isAllSelectedStateChange(false)
                when (navBackStackEntry?.destination?.route) {
                    ROUTE_MAP -> navController.navigate(ROUTE_MAP)
                    ROUTE_COMPANIES -> navController.navigate(ROUTE_COMPANIES)
                    ROUTE_GROUPS -> navController.navigate(ROUTE_GROUPS)
                }
            },
            Modifier.weight(10F),
        ) {
            Text(
                stringResource(R.string.unselect_all),
                color = DarkGrey,
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Composable
fun BusinessSectorChipGroup(
    businessSectors: List<BusinessSector>,
    isAllSelected: Boolean?,
    isAllSelectedStateChange: (Boolean?) -> Unit,
    isNothingSelected: Boolean?,
    isNothingSelectedStateChange: (Boolean?) -> Unit,
    navController: NavController,
    navBackStackEntry: NavBackStackEntry?
) {
    FlowRow(
        Modifier.fillMaxWidth(),
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 8.dp,
    ) {
        filteredBusinessSectors = mutableListOf()

        businessSectors.forEach {
            var isSelected by rememberSaveable { mutableStateOf(true) }

            if (isAllSelected == true) isSelected = true
            if (isNothingSelected == true) isSelected = false

            Button(
                {
                    isSelected = !isSelected
                    when (navBackStackEntry?.destination?.route) {
                        ROUTE_MAP -> navController.navigate(ROUTE_MAP)
                        ROUTE_COMPANIES -> navController.navigate(ROUTE_COMPANIES)
                        ROUTE_GROUPS -> navController.navigate(ROUTE_GROUPS)
                    }
                },
                colors =
                if (isSelected) ButtonDefaults.buttonColors(Color(parseColor(it.color)))
                else ButtonDefaults.buttonColors(DarkWhite),
                shape = RoundedCornerShape(50.dp)
            ) { Text(it.name, color = White, style = MaterialTheme.typography.button) }

            isAllSelectedStateChange(null)
            isNothingSelectedStateChange(null)
            if (isSelected) filteredBusinessSectors.add(it.id)
        }
    }
}

@Composable
fun GroupChipGroup(
    groups: List<Group>,
    isAllSelected: Boolean?,
    isAllSelectedStateChange: (Boolean?) -> Unit,
    isNothingSelected: Boolean?,
    isNothingSelectedStateChange: (Boolean?) -> Unit,
    navController: NavController,
    navBackStackEntry: NavBackStackEntry?
) {
    FlowRow(
        Modifier.fillMaxWidth(),
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 8.dp,
    ) {
        var isIndependentSelected by rememberSaveable { mutableStateOf(true) }
        if (isAllSelected == true) isIndependentSelected = true
        if (isNothingSelected == true) isIndependentSelected = false

        Button(
            {
                isIndependentSelected = !isIndependentSelected
                when (navBackStackEntry?.destination?.route) {
                    ROUTE_MAP -> navController.navigate(ROUTE_MAP)
                    ROUTE_COMPANIES -> navController.navigate(ROUTE_COMPANIES)
                    ROUTE_GROUPS -> navController.navigate(ROUTE_GROUPS)
                }
            },
            colors =
            if (isIndependentSelected) ButtonDefaults.buttonColors(Grey)
            else ButtonDefaults.buttonColors(DarkWhite),
            shape = RoundedCornerShape(50.dp)
        ) { Text("Independent", color = White, style = MaterialTheme.typography.button) }

        isIndependentChipChecked = isIndependentSelected
        filteredGroups = mutableListOf()

        groups.forEach {
            var isSelected by rememberSaveable { mutableStateOf(true) }
            if (isAllSelected == true) isSelected = true
            if (isNothingSelected == true) isSelected = false

            Button(
                {
                    isSelected = !isSelected
                    when (navBackStackEntry?.destination?.route) {
                        ROUTE_MAP -> navController.navigate(ROUTE_MAP)
                        ROUTE_COMPANIES -> navController.navigate(ROUTE_COMPANIES)
                        ROUTE_GROUPS -> navController.navigate(ROUTE_GROUPS)
                    }
                },
                colors =
                if (isSelected) ButtonDefaults.buttonColors(Grey)
                else ButtonDefaults.buttonColors(DarkWhite),
                shape = RoundedCornerShape(50.dp)
            ) { Text(it.name, color = White) }

            isAllSelectedStateChange(null)
            isNothingSelectedStateChange(null)
            if (isSelected) filteredGroups.add(it.id)
        }
    }
}
