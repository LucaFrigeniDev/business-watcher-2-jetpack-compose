package com.example.composetest.screens.creation.screenComponents

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.composetest.R
import com.example.composetest.displayScreen
import com.example.composetest.isConnectedToTheNetwork
import com.example.composetest.toast
import com.example.composetest.screens.creation.company.ui.CompanyCreationViewModel
import com.example.composetest.screens.creation.group.ui.GroupCreationViewModel
import com.example.composetest.theme.standardColumnModifier
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ValidationFAB(
    companyViewModel: CompanyCreationViewModel?,
    groupViewModel: GroupCreationViewModel?,
    navController: NavController
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val navigate = {
        scope.launch {
            delay(750)
            navController.navigate(displayScreen)
        }
    }

    Column(standardColumnModifier, Arrangement.Bottom, Alignment.End) {
        FloatingActionButton({

            if (context.isConnectedToTheNetwork()) {
                if (companyViewModel != null)
                    companyValidation(companyViewModel, context, navigate)
                else
                    groupValidation(groupViewModel!!, context, navigate)
            } else
                context.toast(R.string.connectivity_error)

        }) { Icon(Icons.Default.Done, "") }
    }
}

fun companyValidation(
    viewModel: CompanyCreationViewModel,
    context: Context,
    navigate: () -> Job
) {
    viewModel.checkAndCreate(context)

    if (viewModel.error.value == "company registered") {
        Toast.makeText(context, viewModel.error.value, Toast.LENGTH_LONG).show()
        navigate.invoke()
    } else
        Toast.makeText(context, viewModel.error.value, Toast.LENGTH_LONG).show()
}

fun groupValidation(
    viewModel: GroupCreationViewModel,
    context: Context,
    navigate: () -> Job
) {
    if (viewModel.isCorrectlyField().value == "OK") {
        viewModel.createGroup()
        navigate.invoke()
    } else
        Toast.makeText(context, viewModel.isCorrectlyField().value, Toast.LENGTH_LONG).show()
}