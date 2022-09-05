package com.example.composetest.screens.creation.screenComponents

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.composetest.R
import com.example.composetest.setPhotoUrl
import com.example.composetest.screens.creation.company.ui.CompanyCreationViewModel
import com.example.composetest.screens.creation.group.ui.GroupCreationViewModel
import com.example.composetest.theme.DarkGrey
import com.example.composetest.theme.White

@Composable
fun CompanyLogoSection(viewModel: CompanyCreationViewModel) {
    val groupLogo: String by viewModel.groupLogo.collectAsState()
    val independentLogo: Uri? by viewModel.independentLogoUri.collectAsState()

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        viewModel.setIndependentLogo(it)
    }

    val delete = { viewModel.setIndependentLogo(null) }

    Spacer(Modifier.padding(10.dp))

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        if (groupLogo.isNotBlank()) {
            DisplayedLogo(setPhotoUrl(groupLogo))

        } else if (independentLogo != null) {
            DisplayedLogo(independentLogo!!)
            DeleteLogoButton(delete)

        } else {
            AddLogoButton(launcher)
        }
    }

    Spacer(Modifier.padding(10.dp))
}

@Composable
fun GroupLogoSection(viewModel: GroupCreationViewModel) {
    val logo: Uri? by viewModel.logo.collectAsState()
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        viewModel.setLogo(it)
    }

    val delete = { viewModel.setLogo(null) }

    Spacer(Modifier.padding(10.dp))

    if (logo != null) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            DisplayedLogo(logo!!)
            DeleteLogoButton(delete)
        }
    } else {
        AddLogoButton(launcher)
    }

    Spacer(Modifier.padding(10.dp))
}

@Composable
fun DisplayedLogo(logo: Any) =
    Image(
        rememberAsyncImagePainter(logo),
        "",
        Modifier
            .size(150.dp)
            .clip(RoundedCornerShape(50)),
        contentScale = ContentScale.Crop
    )

@Composable
fun DeleteLogoButton(delete: () -> Unit) =
    FloatingActionButton(
        { delete.invoke() },
        Modifier.size(30.dp)
    ) {
        Icon(Icons.Default.Delete, "")
    }

@Composable
fun AddLogoButton(launcher: ManagedActivityResultLauncher<String, Uri?>) =
    Button(
        { launcher.launch("image/*") },
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 60.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(DarkGrey)
    ) {
        Text(
            stringResource(R.string.import_logo),
            color = White,
            style = MaterialTheme.typography.body1
        )
    }