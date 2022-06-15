package com.example.composetest.ui.screens.creationScreens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.composetest.R
import com.example.composetest.ui.base.BackBtn
import com.example.composetest.ui.base.Title
import com.example.composetest.ui.theme.*

@Composable
fun GroupCreationScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<GroupCreationViewModel>()
    val scrollState = rememberScrollState()

    Column(standardColumnModifier.verticalScroll(scrollState)) {
        BackBtn(navController)

        Title(stringResource(R.string.create_group_title))

        Spacer(Modifier.padding(20.dp))

        GroupNameTextField(viewModel)

        DescriptionTextField(viewModel)

        Spacer(Modifier.padding(10.dp))

        LogoSection(viewModel)

        Spacer(Modifier.padding(10.dp))
    }

    GroupValidationFAB(viewModel, navController)
}

@Composable
fun GroupNameTextField(viewModel: GroupCreationViewModel) {
    var name by rememberSaveable { mutableStateOf("") }

    TextField(
        name,
        { name = it; viewModel.setName(it) },
        modifierFullLength,
        label = {
            Text(
                stringResource(R.string.name),
                color = White,
                style = MaterialTheme.typography.body1
            )
        },
        singleLine = true,
        colors = textFieldColors(White, backgroundColor = LightGrey)
    )
}

@Composable
fun DescriptionTextField(viewModel: GroupCreationViewModel) {
    var description by rememberSaveable { mutableStateOf("") }

    TextField(
        description,
        { description = it; viewModel.setDescription(it) },
        modifierHeight,
        label = {
            Text(
                stringResource(R.string.description),
                color = White,
                style = MaterialTheme.typography.body1
            )
        },
        colors = textFieldColors(White, backgroundColor = LightGrey)
    )
}

@Composable
fun LogoSection(viewModel: GroupCreationViewModel) {
    var uri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        uri = it
        viewModel.setLogo(it)
    }

    if (uri != null) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                rememberAsyncImagePainter(uri),
                "",
                Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(50)),
                contentScale = ContentScale.Crop
            )

            FloatingActionButton(
                {
                    uri = null
                    viewModel.setLogo(null)
                },
                Modifier.size(30.dp)
            ) { Icon(imageVector = Icons.Default.Delete, contentDescription = "") }
        }
    } else {
        Button(
            { launcher.launch("image/*") },
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp),
            shape = RoundedCornerShape(16.dp),
            colors = buttonColors(DarkGrey)
        ) {
            Text(
                stringResource(R.string.import_logo),
                color = White,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun GroupValidationFAB(viewModel: GroupCreationViewModel, navController: NavController) {
    val context = LocalContext.current

    Column(standardColumnModifier, Arrangement.Bottom, Alignment.End) {
        FloatingActionButton({
            if (viewModel.isCorrectlyField().value == "OK") {
                viewModel.insert()
                navController.popBackStack()
            } else Toast.makeText(context, viewModel.isCorrectlyField().value, Toast.LENGTH_LONG)
                .show()
        }) {
            Icon(Icons.Default.Done, "")
        }
    }
}