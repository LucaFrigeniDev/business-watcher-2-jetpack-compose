package com.example.composetest.ui.base

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.composetest.ui.ROUTE_COMPANIES
import com.example.composetest.ui.ROUTE_GROUPS
import com.example.composetest.ui.ROUTE_MAP
import com.example.composetest.ui.theme.DarkWhite
import com.example.composetest.ui.theme.Grey
import com.example.composetest.ui.theme.LightWhite

var searchBarFilter = ""

@Composable
fun SearchBar(navController: NavController, navBackStackEntry: NavBackStackEntry?) {
    var text by rememberSaveable { mutableStateOf(searchBarFilter) }

    Card(
        Modifier
            .fillMaxWidth()
            .height(78.dp)
            .padding(top = 19.dp, start = 34.dp, end = 29.dp),
        RoundedCornerShape(50),
        DarkWhite,
        elevation = 80.dp
    ) {}

    OutlinedTextField(
        text,
        {
            text = it
            searchBarFilter = it
            when (navBackStackEntry?.destination?.route) {
                ROUTE_MAP -> navController.navigate(ROUTE_MAP)
                ROUTE_COMPANIES -> navController.navigate(ROUTE_COMPANIES)
                ROUTE_GROUPS -> navController.navigate(ROUTE_GROUPS)
            }
        },
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 10.dp),
        textStyle = MaterialTheme.typography.body1,
        label = { Text("search", color = Grey, style = MaterialTheme.typography.body1) },
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            Grey,
            cursorColor = Grey,
            focusedBorderColor = LightWhite,
            unfocusedBorderColor = LightWhite,
            backgroundColor = LightWhite
        ),
        leadingIcon = { Icon(Icons.Default.Search, "", tint = Grey) }
    )
}