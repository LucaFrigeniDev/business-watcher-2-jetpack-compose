package com.example.composetest.ui.screens.detailsScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.composetest.R
import com.example.composetest.setPhotoUrl
import com.example.composetest.ui.base.BackBtn
import com.example.composetest.ui.theme.DarkGrey
import com.example.composetest.ui.theme.Grey
import com.example.composetest.ui.theme.LightGrey
import com.example.composetest.ui.theme.White

@Composable
fun DetailScreenHeader(
    navController: NavController,
    logo: String,
    name: String,
    groupName: String?,
    description: String,
    companyDetailViewModel: CompanyDetailViewModel?,
    groupDetailViewModel: GroupDetailViewModel?
) {
    Column(Modifier.fillMaxSize()) {
        BackBtn(navController)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            AsyncImage(
                setPhotoUrl(logo),
                "",
                Modifier
                    .background(White, RoundedCornerShape(50))
                    .size(200.dp)
                    .clip(RoundedCornerShape(50)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.padding(10.dp))

            Text(name, style = MaterialTheme.typography.h2)

            if (groupName != null)
                Text(groupName, style = MaterialTheme.typography.subtitle2)

            Spacer(Modifier.padding(10.dp))

            DescriptionCard(description, companyDetailViewModel, groupDetailViewModel)
        }
    }
}

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
                    .padding(10.dp), horizontalAlignment = Alignment.End
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

@Composable
fun DescriptionUpdateDialog(
    openDialog: MutableState<Boolean>,
    description: String,
    companyDetailViewModel: CompanyDetailViewModel?,
    groupDetailViewModel: GroupDetailViewModel?
) = AlertDialog(
    { openDialog.value = false },
    {
        Column {

            Spacer(Modifier.padding(6.dp))

            Text(
                stringResource(R.string.description_dialog_message),
                Modifier.fillMaxWidth(),
                Grey,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.padding(6.dp))
            DescriptionTextField(description, companyDetailViewModel, groupDetailViewModel)
            Spacer(Modifier.padding(6.dp))
        }

        Row(
            Modifier
                .fillMaxSize()
                .padding(end = 16.dp),
            Arrangement.End
        ) {

            TextButton({ openDialog.value = false }) {
                Text(
                    stringResource(R.string.add_sector_dialog_negative_btn),
                    color = Grey,
                    style = MaterialTheme.typography.button
                )
            }

            Spacer(Modifier.padding(4.dp))

            TextButton(
                {
                    if (companyDetailViewModel != null) companyDetailViewModel.updateDescription()
                    else groupDetailViewModel!!.updateDescription()

                    openDialog.value = false
                },
                Modifier.background(DarkGrey, RoundedCornerShape(50.dp)).padding(horizontal = 4.dp)
            ) {
                Text(
                    stringResource(R.string.description_dialog_positive_btn),
                    color = White,
                    style = MaterialTheme.typography.button
                )
            }
        }
    },
    Modifier.height(270.dp),
    shape = RoundedCornerShape(16.dp),
    backgroundColor = White,
    properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
)

@Composable
fun DescriptionTextField(
    description: String,
    companyDetailViewModel: CompanyDetailViewModel?,
    groupDetailViewModel: GroupDetailViewModel?
) {
    var text by rememberSaveable { mutableStateOf(description) }

    TextField(
        text,
        {
            text = it
            if (companyDetailViewModel != null) companyDetailViewModel.setCompanyDescription(it)
            else groupDetailViewModel!!.setDescription(it)
        },
        Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 10.dp),
        textStyle = MaterialTheme.typography.body2,
        label = {
            Text(
                stringResource(R.string.description),
                color = White,
                style = MaterialTheme.typography.body1
            )
        },
        colors = TextFieldDefaults.textFieldColors(textColor = White, backgroundColor = LightGrey)
    )
}
