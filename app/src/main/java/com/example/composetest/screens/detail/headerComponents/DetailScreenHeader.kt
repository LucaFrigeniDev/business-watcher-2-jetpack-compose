package com.example.composetest.screens.detail.headerComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.composetest.setPhotoUrl
import com.example.composetest.base.BackBtn
import com.example.composetest.screens.detail.company.ui.CompanyDetailViewModel
import com.example.composetest.screens.detail.group.ui.GroupDetailViewModel
import com.example.composetest.theme.White

@Composable
fun DetailScreenHeader(
    navController: NavController,
    logo: String,
    name: String,
    groupName: String?,
    description: String,
    companyDetailViewModel: CompanyDetailViewModel?,
    groupDetailViewModel: GroupDetailViewModel?
) {
    Column(Modifier.fillMaxSize()) {
        BackBtn(navController)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            AsyncImage(
                setPhotoUrl(logo),
                "",
                Modifier
                    .background(White, RoundedCornerShape(50))
                    .size(200.dp)
                    .clip(RoundedCornerShape(50)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.padding(10.dp))

            Text(name, style = MaterialTheme.typography.h2)

            if (groupName != null)
                Text(groupName, style = MaterialTheme.typography.subtitle2)

            Spacer(Modifier.padding(10.dp))

            DescriptionCard(description, companyDetailViewModel, groupDetailViewModel)
        }
    }
}