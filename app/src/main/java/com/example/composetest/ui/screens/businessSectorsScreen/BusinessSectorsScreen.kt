package com.example.composetest.ui.screens.businessSectorsScreen

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.composetest.R
import com.example.composetest.domain.relations.BusinessSectorWithCompanies
import com.example.composetest.ui.base.BackBtn
import com.example.composetest.ui.base.EmptyScreen
import com.example.composetest.ui.base.Title
import com.example.composetest.ui.theme.*
import java.time.format.TextStyle

@Composable
fun BusinessSectorScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<BusinessSectorViewModel>()
    val size by viewModel.size.collectAsState(0)
    val list by viewModel.businessSectors().collectAsState(emptyList())

    val scrollState = rememberScrollState()

    Column(standardColumnModifier.verticalScroll(scrollState)) {

        BackBtn(navController)

        Title(stringResource(R.string.sector))

        Spacer(Modifier.padding(20.dp))

        if (size == 0) {
            EmptyScreen(stringResource(R.string.empty_sector_list), Grey, Modifier.size(250.dp))
        } else {
            for (businessSector in list) {
                BusinessSectorItemCard(businessSector)
                Spacer(Modifier.padding(10.dp))
            }
        }
    }

    if (size < 5) AddBusinessSectorFAB(viewModel)
}

@Composable
fun BusinessSectorItemCard(businessSector: BusinessSectorWithCompanies) = Card(
    Modifier
        .fillMaxSize()
        .height(110.dp),
    RoundedCornerShape(15),
    Color(parseColor(businessSector.businessSector.color))
) {
    BusinessSectorCardContent(businessSector)
}

@Composable
fun BusinessSectorCardContent(businessSector: BusinessSectorWithCompanies) =
    Column(Modifier.padding(start = 26.dp), Arrangement.Center) {
        Text(
            businessSector.businessSector.name,
            Modifier
                .weight(3F)
                .padding(top = 10.dp),
            White,
            style = MaterialTheme.typography.subtitle1
        )

        Text(
            stringResource(R.string.sector_companies, businessSector.companies.size.toString()),
            Modifier.weight(2F),
            White,
            style = MaterialTheme.typography.body2
        )

        Text(
            stringResource(
                R.string.sector_turnover,
                businessSector.companies.sumOf { it.turnover }.toString()
            ),
            Modifier.weight(2F),
            White,
            style = MaterialTheme.typography.body2
        )
    }

@Composable
fun AddBusinessSectorFAB(viewModel: BusinessSectorViewModel) {
    val isDialogOpen = rememberSaveable { mutableStateOf(false) }

    Column(
        standardColumnModifier,
        Arrangement.Bottom,
        Alignment.End
    ) {

        FloatingActionButton({ isDialogOpen.value = true }) {
            Icon(Icons.Default.Add, "")
        }
    }

    if (isDialogOpen.value)
        AddBusinessSectorDialog(isDialogOpen, viewModel)
}

@Composable
fun AddBusinessSectorDialog(isOpen: MutableState<Boolean>, viewModel: BusinessSectorViewModel) =
    AlertDialog(
        { isOpen.value = false },
        {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                Arrangement.End
            ) {

                TextButton({ isOpen.value = false }) {
                    Text(
                        stringResource(R.string.add_sector_dialog_negative_btn),
                        color = DarkGrey,
                        style = MaterialTheme.typography.button
                    )
                }

                Spacer(Modifier.padding(4.dp))

                TextButton(
                    {
                        viewModel.insert()
                        isOpen.value = false
                    },
                    Modifier.background(DarkGrey, RoundedCornerShape(50.dp))
                ) {
                    Text(
                        stringResource(R.string.add_sector_dialog_positive_btn),
                        color = White,
                        style = MaterialTheme.typography.button
                    )
                }
            }
        },
        Modifier.fillMaxWidth().height(210.dp),
        {
            Column {

                Text(
                    stringResource(R.string.add_sector_dialog_title),
                    Modifier.fillMaxWidth(),
                    Grey,
                    style = MaterialTheme.typography.subtitle1,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.padding(10.dp))
                BusinessSectorTextField(viewModel)
                Spacer(Modifier.padding(10.dp))
            }
        },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = White,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    )

@Composable
fun BusinessSectorTextField(viewModel: BusinessSectorViewModel) {
    var name by rememberSaveable { mutableStateOf("") }

    TextField(
        name,
        {
            name = it
            viewModel.setName(it)
        },
        Modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.body2,
        label = {
            Text(
                stringResource(R.string.name),
                color = White,
                style = MaterialTheme.typography.body1
            )
        },
        colors = TextFieldDefaults.textFieldColors(textColor = White, backgroundColor = LightGrey)
    )
}

