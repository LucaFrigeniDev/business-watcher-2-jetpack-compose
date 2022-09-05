package com.example.composetest.screens.lists.group_list.ui.screenComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.composetest.R
import com.example.composetest.screens.lists.group_list.domain.GroupWithCompanies
import com.example.composetest.numberFormat
import com.example.composetest.setPhotoUrl
import com.example.composetest.theme.Grey
import com.example.composetest.theme.White

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
                    stringResource(
                        R.string.euro,
                        numberFormat(group.companies.sumOf { it.turnover })
                    ),
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