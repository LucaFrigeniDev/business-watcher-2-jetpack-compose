@file:OptIn(ExperimentalMaterialApi::class)

package com.example.composetest.ui.screens.creationScreens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.composetest.R
import com.example.composetest.setPhotoUrl
import com.example.composetest.ui.base.BackBtn
import com.example.composetest.ui.base.Title
import com.example.composetest.ui.theme.*

@Composable
fun CompanyCreationScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<CompanyCreationViewModel>()
    val scrollState = rememberScrollState()

    Column(
        Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        BackBtn(navController)

        Title(stringResource(R.string.create_company_title))

        Spacer(Modifier.padding(20.dp))

        TextField(stringResource(R.string.name), 1, viewModel)
        Spacer(Modifier.padding(2.dp))
        DropdownMenu(stringResource(R.string.sector), 2, viewModel)
        Spacer(Modifier.padding(2.dp))
        DropdownMenu(stringResource(R.string.group), 1, viewModel)

        Spacer(Modifier.padding(10.dp))

        CompanyLogoSection(viewModel)

        Spacer(Modifier.padding(10.dp))

        Row(Modifier.fillMaxWidth(), Arrangement.End) {
            TextField(stringResource(R.string.postal_code), 6, viewModel)
            Spacer(Modifier.padding(2.dp))
            TextField(stringResource(R.string.city), 4, viewModel)
        }

        Spacer(Modifier.padding(2.dp))

        TextField(stringResource(R.string.address), 2, viewModel)

        Spacer(Modifier.padding(10.dp))

        TextField(stringResource(R.string.turnover), 3, viewModel)
        Spacer(Modifier.padding(2.dp))
        TextField(stringResource(R.string.description), 5, viewModel)

        Spacer(Modifier.padding(10.dp))
    }
    ValidationFAB(viewModel, navController)
}

@Composable
fun TextField(label: String, int: Int, viewModel: CompanyCreationViewModel) {
    var text by rememberSaveable { mutableStateOf("") }
    val keyboardType =
        if (int == 6)
            KeyboardOptions(keyboardType = KeyboardType.Number)
        else
            KeyboardOptions(keyboardType = KeyboardType.Text)

    TextField(
        text,
        {
            when (int) {
                1 -> if (it.length < 25) viewModel.setName(it)
                2 -> if (it.length < 25) viewModel.setAddress(it)
                3 -> if (it.length < 15) viewModel.setTurnover(it.toInt())
                4 -> if (it.length < 15) viewModel.setCity(it)
                5 -> if (it.length < 250) viewModel.setDescription(it)
                6 -> if (it.length < 10) viewModel.setPostalCode(it)
            }
            text = it
        },
        when (int) {
            5 -> modifierHeight
            6 -> modifierSmallLength
            else -> modifierFullLength
        },
        textStyle = MaterialTheme.typography.body1,
        label = { Text(label, color = White, style = MaterialTheme.typography.body1) },
        keyboardOptions = keyboardType,
        singleLine = int != 5,
        colors = TextFieldDefaults.textFieldColors(
            White,
            backgroundColor = LightGrey
        )
    )
}

@Composable
fun DropdownMenu(label: String, int: Int, viewModel: CompanyCreationViewModel) {
    var selected by rememberSaveable { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val groups: List<String> by viewModel.groupsNames.collectAsState()
    val sectors: List<String> by viewModel.sectorsNames.collectAsState()

    ExposedDropdownMenuBox(
        expanded,
        { expanded = !expanded }
    ) {
        TextField(
            selected,
            {},
            modifierFullLength,
            readOnly = true,
            label = { Text(label, color = White, style = MaterialTheme.typography.body1) },
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
            when (int) {
                1 -> {
                    groups.forEach {
                        DropdownMenuItem(
                            {
                                selected = it
                                expanded = false
                                viewModel.setGroup(it)
                            }
                        ) { Text(it, style = MaterialTheme.typography.body1) }
                    }
                }
                2 -> {
                    sectors.forEach {
                        DropdownMenuItem(
                            {
                                selected = it
                                expanded = false
                                viewModel.setSector(it)
                            }
                        ) { Text(it, style = MaterialTheme.typography.body1) }
                    }
                }
            }
        }
    }
}

@Composable
fun CompanyLogoSection(viewModel: CompanyCreationViewModel) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        viewModel.setIndependentLogo(it)
    }

    val groupLogo: String by viewModel.groupLogo.collectAsState()
    val independentLogo: Uri? by viewModel.independentLogoUri.collectAsState()


    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        if (groupLogo.isNotBlank()) {
            AsyncImage(
                setPhotoUrl(groupLogo),
                "",
                Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(50)),
                contentScale = ContentScale.Crop
            )
        } else if (independentLogo != null) {
            Image(
                rememberAsyncImagePainter(independentLogo),
                "",
                Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(50)),
                contentScale = ContentScale.Crop
            )

            FloatingActionButton(
                { viewModel.setIndependentLogo(null) },
                Modifier.size(30.dp)
            ) {
                Icon(Icons.Default.Delete, "")
            }
        } else {
            Button(
                { launcher.launch("image/*") },
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = DarkGrey)
            ) {
                Text(
                    stringResource(R.string.import_logo),
                    color = White,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Composable
fun ValidationFAB(viewModel: CompanyCreationViewModel, navController: NavController) {
    val context = LocalContext.current

    Column(standardColumnModifier, Arrangement.Bottom, Alignment.End) {
        FloatingActionButton({
            if (viewModel.isCorrectlyField().value) {
                viewModel.geoLocate(context)
                if (viewModel.isCorrectlyGeolocated().value) {
                    viewModel.insert()
                    navController.popBackStack()
                } else Toast.makeText(context, "", Toast.LENGTH_LONG).show()
            } else Toast.makeText(context, "error", Toast.LENGTH_LONG).show()
        }) { Icon(Icons.Default.Done, "") }
    }
}