package com.example.composetest.base

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composetest.R
import com.example.composetest.displayScreen
import com.example.composetest.theme.DarkGrey

@Composable
fun BackBtn(navController: NavController) =
    IconButton(
        { navController.navigate(displayScreen) },
        Modifier.padding(4.dp)
    ) {
        Icon(
            Icons.Default.KeyboardArrowLeft,
            "",
            tint = DarkGrey
        )
    }

@Composable
fun Title(text: String) {
    Text(
        text,
        Modifier.fillMaxWidth(),
        DarkGrey,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h2
    )
    Spacer(Modifier.padding(20.dp))
}

@Composable
fun EmptyScreen(text: String, color: Color, modifier: Modifier) = Column(
    Modifier.fillMaxSize(),
    Arrangement.Center,
    Alignment.CenterHorizontally
) {

    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Text(
            text,
            color = color,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1
        )
    } else {
        Image(
            painterResource(R.drawable.empty),
            "",
            modifier
        )

        Text(
            text,
            color = color,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1
        )
    }
}

