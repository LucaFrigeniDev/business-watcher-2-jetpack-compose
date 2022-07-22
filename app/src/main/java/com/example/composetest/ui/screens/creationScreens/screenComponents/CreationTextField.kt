package com.example.composetest.ui.screens.creationScreens.screenComponents

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.composetest.ui.theme.LightGrey
import com.example.composetest.ui.theme.White
import com.example.composetest.ui.theme.modifierFullLength
import kotlinx.coroutines.Job

@Composable
fun CreationTextField(
    hint: String,
    text: String,
    textChange: (String) -> Unit,
    updater: () -> Unit,
    maxLength: Int,
    singleLine: Boolean,
    modifier: Modifier,
    isNumericOnly: Boolean
) {
    val keyboardType =
        if (isNumericOnly)
            KeyboardOptions(keyboardType = KeyboardType.Number)
        else
            KeyboardOptions(keyboardType = KeyboardType.Text)

    TextField(
        text,
        {
            if (it.length < maxLength) textChange(it)
            updater.invoke()
        },
        modifier,
        textStyle = MaterialTheme.typography.body1,
        label = {
            Text(
                hint,
                color = White,
                style = MaterialTheme.typography.body1
            )
        },
        keyboardOptions = keyboardType,
        singleLine = singleLine,
        colors = TextFieldDefaults.textFieldColors(White, backgroundColor = LightGrey)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreationDropDownMenu(
    label: String,
    list: List<String>,
    selectedItem: String,
    selection: (String) -> Unit,
    selector: () -> Job
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded,
        { expanded = !expanded }
    ) {
        TextField(
            selectedItem,
            {},
            modifierFullLength,
            readOnly = true,
            textStyle = MaterialTheme.typography.body1,
            label = {
                Text(label, color = White, style = MaterialTheme.typography.body1)
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                textColor = White,
                backgroundColor = LightGrey
            )
        )

        ExposedDropdownMenu(
            expanded,
            { expanded = false }
        ) {
            list.forEach {
                DropdownMenuItem(
                    {
                        selection(it)
                        selector.invoke()
                        expanded = false
                    }
                ) { Text(it, style = MaterialTheme.typography.body1) }
            }
        }
    }
}