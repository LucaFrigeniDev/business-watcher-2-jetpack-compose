package com.example.composetest.screens.detail.headerComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.composetest.R
import com.example.composetest.screens.detail.company.ui.CompanyDetailViewModel
import com.example.composetest.screens.detail.group.ui.GroupDetailViewModel
import com.example.composetest.theme.DarkGrey
import com.example.composetest.theme.Grey
import com.example.composetest.theme.LightGrey
import com.example.composetest.theme.White

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
                    if (companyDetailViewModel != null)
                        companyDetailViewModel.updateDescription()
                    else
                        groupDetailViewModel!!.updateDescription()

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
            if (it.length < 250) text = it

            if (companyDetailViewModel != null) {
                companyDetailViewModel.setCompanyDescription(it)
            } else {
                groupDetailViewModel!!.setDescription(it)
            }
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