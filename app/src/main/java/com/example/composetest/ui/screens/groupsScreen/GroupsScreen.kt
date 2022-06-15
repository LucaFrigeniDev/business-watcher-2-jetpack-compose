package com.example.composetest.ui.screens.groupsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.composetest.R
import com.example.composetest.domain.relations.GroupWithCompanies
import com.example.composetest.setPhotoUrl
import com.example.composetest.ui.base.EmptyScreen
import com.example.composetest.ui.screens.companiesScreens.ChangeFAB
import com.example.composetest.ui.theme.Grey
import com.example.composetest.ui.theme.LightWhite
import com.example.composetest.ui.theme.White

@Composable
fun GroupsScreen(navController: NavController, navBackStackEntry: NavBackStackEntry?) {
    val viewModel = hiltViewModel<GroupsViewModel>()
    val groups by viewModel.groups.collectAsState()

    if (groups.isNotEmpty())
        GroupsList(navController, groups)
    else
        EmptyScreen(stringResource(R.string.empty_group_list), Grey, Modifier.size(250.dp))

    ChangeFAB(navController, navBackStackEntry)
}

@Composable
fun GroupsList(navController: NavController, groups: List<GroupWithCompanies>) =
    LazyColumn(contentPadding = PaddingValues(vertical = 100.dp, horizontal = 18.dp)) {
        items(groups) { group -> GroupItemCard(navController, group) }
    }

@Composable
fun GroupItemCard(navController: NavController, group: GroupWithCompanies) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .height(100.dp)
            .clickable { navController.navigate("group?id=${group.group.id}") },
    ) {
        Card(
            Modifier
                .fillMaxSize()
                .padding(start = 50.dp, top = 6.dp, bottom = 6.dp),
            RoundedCornerShape(20),
            Grey
        ) {
            Column(Modifier.padding(start = 70.dp, top = 4.dp)) {
                Text(
                    group.group.name,
                    Modifier.weight(1F),
                    White,
                    maxLines = 1,
                    style = MaterialTheme.typography.subtitle1
                )

                Text(
                    stringResource(R.string.euro, group.companies.sumOf { it.turnover }.toString()),
                    Modifier.weight(1F),
                    White,
                    maxLines = 1,
                    style = MaterialTheme.typography.body2
                )

                Text(
                    stringResource(R.string.group_companies, group.companies.size.toString()),
                    Modifier.weight(1F),
                    White,
                    maxLines = 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }

        AsyncImage(
            setPhotoUrl(group.group.logo),
            "",
            Modifier
                .background(White, RoundedCornerShape(50))
                .size(100.dp)
                .clip(RoundedCornerShape(50)),
            contentScale = ContentScale.Crop
        )
    }
}

