package com.example.composetest.ui.screens.businessSectorsScreen.screenComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.composetest.R
import com.example.composetest.toast
import com.example.composetest.ui.screens.businessSectorsScreen.BusinessSectorViewModel
import com.example.composetest.ui.theme.DarkGrey
import com.example.composetest.ui.theme.Grey
import com.example.composetest.ui.theme.LightGrey
import com.example.composetest.ui.theme.White

@Composable
fun AddBusinessSectorDialog(isOpen: MutableState<Boolean>, viewModel: BusinessSectorViewModel) {
    val context = LocalContext.current

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
                        if (viewModel.name.value.isNotBlank()) {
                            viewModel.insert()
                            isOpen.value = false
                        } else {
                            context.toast(R.string.sector_name_error)
                        }
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

            Spacer(Modifier.size(8.dp))
        },
        Modifier
            .fillMaxWidth()
            .height(210.dp),
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
}

@Composable
fun BusinessSectorTextField(viewModel: BusinessSectorViewModel) {
    var name by rememberSaveable { mutableStateOf("") }

    TextField(
        name,
        {
            if (it.length < 25) name = it
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