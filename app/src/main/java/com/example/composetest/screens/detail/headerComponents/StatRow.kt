package com.example.composetest.screens.detail.headerComponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun StatRow(iconId: Int, text: String) {
    Row {
        Icon(
            painterResource(iconId),
            "",
            Modifier.size(30.dp)
        )

        Spacer(Modifier.padding(4.dp))

        Text(
            text,
            Modifier.padding(top = 4.dp),
            style = MaterialTheme.typography.body1
        )
    }
    Spacer(Modifier.padding(4.dp))
}