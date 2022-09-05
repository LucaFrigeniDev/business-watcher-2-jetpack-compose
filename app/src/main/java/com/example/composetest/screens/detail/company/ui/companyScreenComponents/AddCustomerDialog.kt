package com.example.composetest.screens.detail.company.ui.companyScreenComponents

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
import com.example.composetest.theme.DarkGrey
import com.example.composetest.theme.Grey
import com.example.composetest.theme.LightGrey
import com.example.composetest.theme.White

@Composable
fun AddCustomerDialog(openDialog: MutableState<Boolean>, viewModel: CompanyDetailViewModel) =
    AlertDialog(
        { openDialog.value = false },
        {
            Column {
                Spacer(Modifier.padding(6.dp))
                Text(
                    stringResource(R.string.add_customer),
                    Modifier.fillMaxWidth(),
                    Grey,
                    style = MaterialTheme.typography.subtitle1,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.padding(6.dp))
                CustomerNameTextField(viewModel)
                Spacer(Modifier.padding(4.dp))
                CustomerDescriptionTextField(viewModel)
                Spacer(Modifier.padding(6.dp))
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(end = 16.dp), Arrangement.End) {
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
                            viewModel.addCustomer()
                            openDialog.value = false
                        },
                        Modifier.background(DarkGrey, RoundedCornerShape(50.dp))
                    ) {
                        Text(
                            text = stringResource(R.string.add_sector_dialog_positive_btn),
                            color = White,
                            style = MaterialTheme.typography.button
                        )
                    }
                }
            }
        },
        Modifier.height(320.dp),
        backgroundColor = White,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
        shape = RoundedCornerShape(16.dp),
    )

@Composable
fun CustomerNameTextField(viewModel: CompanyDetailViewModel) {
    var name by rememberSaveable { mutableStateOf("") }

    TextField(
        name,
        {
            if (it.length < 25) name = it
            viewModel.setCustomerName(it)
        },
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        textStyle = MaterialTheme.typography.body2,
        label = {
            Text(
                stringResource(R.string.name),
                color = White,
                style = MaterialTheme.typography.body1
            )
        },
        colors = TextFieldDefaults.textFieldColors(White, backgroundColor = LightGrey)
    )
}

@Composable
fun CustomerDescriptionTextField(viewModel: CompanyDetailViewModel) {
    var description by rememberSaveable { mutableStateOf("") }

    TextField(
        description,
        {
            if (it.length < 250) description = it
            viewModel.setCustomerDescription(it)
        },
        Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 10.dp),
        textStyle = MaterialTheme.typography.body2,
        label = {
            Text(
                stringResource(R.string.description),
                color = White,
                style = MaterialTheme.typography.body1
            )
        },
        colors = TextFieldDefaults.textFieldColors(White, backgroundColor = LightGrey)
    )
}