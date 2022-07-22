package com.example.composetest.ui.screens.detailsScreens.headerComponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composetest.R
import com.example.composetest.ui.screens.detailsScreens.CompanyDetailViewModel
import com.example.composetest.ui.screens.detailsScreens.GroupDetailViewModel
import com.example.composetest.ui.theme.Grey

@Composable
fun DescriptionCard(
    description: String,
    companyDetailViewModel: CompanyDetailViewModel?,
    groupDetailViewModel: GroupDetailViewModel?
) {
    val openDialog = rememberSaveable { mutableStateOf(false) }

    Column(Modifier.padding(horizontal = 16.dp)) {
        Card(
            Modifier.fillMaxWidth(),
            RoundedCornerShape(10),
            Grey
        ) {
            Column(Modifier.fillMaxSize()) {
                Text(
                    stringResource(R.string.about),
                    Modifier.padding(16.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    description,
                    Modifier.padding(start = 16.dp, bottom = 16.dp, end = 16.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.body1
                )
            }
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.End
            ) {
                FloatingActionButton(
                    { openDialog.value = true },
                    Modifier.size(30.dp)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        "",
                        Modifier.size(18.dp)
                    )
                }
            }
        }
    }

    if (openDialog.value)
        DescriptionUpdateDialog(
            openDialog,
            description,
            companyDetailViewModel,
            groupDetailViewModel
        )
}