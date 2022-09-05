package com.example.composetest.screens.lists.company_list.ui.screenComponent

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.composetest.R
import com.example.composetest.numberFormat
import com.example.composetest.screens.lists.company_list.data.Company
import com.example.composetest.screens.lists.company_list.domain.CompanyItem
import com.example.composetest.setPhotoUrl
import com.example.composetest.theme.Grey
import com.example.composetest.theme.White
import kotlinx.coroutines.launch

@Composable
fun CompaniesList(
    companyItemList: List<CompanyItem>,
    navController: NavController
) = LazyColumn(contentPadding = PaddingValues(vertical = 100.dp, horizontal = 18.dp)) {
    items(companyItemList) { companyItem ->
        CompanyItemCard(companyItem.company, companyItem.businessSector.color, navController)
    }
}

@Composable
fun CompanyItemCard(
    company: Company,
    color: String,
    navController: NavController?
) {
    val scope = rememberCoroutineScope()

    Box(
        Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .height(100.dp)
            .clickable {
                scope.launch {
                    navController?.navigate("company?id=${company.id}")
                }
            },
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
                    company.name,
                    Modifier.weight(1F),
                    White,
                    maxLines = 1,
                    style = MaterialTheme.typography.subtitle1
                )

                Text(
                    stringResource(R.string.euro, numberFormat(company.turnover)),
                    Modifier.weight(1F),
                    White,
                    maxLines = 1,
                    style = MaterialTheme.typography.body2
                )

                Text(
                    company.city,
                    Modifier.weight(1F),
                    White,
                    maxLines = 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }

        AsyncImage(
            setPhotoUrl(company.logo),
            "",
            Modifier
                .background(White, RoundedCornerShape(50))
                .border(
                    6.dp,
                    Color(parseColor(color)),
                    RoundedCornerShape(50)
                )
                .size(100.dp)
                .clip(RoundedCornerShape(50)),
            contentScale = ContentScale.Crop
        )
    }
}