package com.example.composetest.ui.screens.businessSectorsScreen.screenComponents

import android.graphics.Color.parseColor
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composetest.R
import com.example.composetest.domain.relations.BusinessSectorWithCompanies
import com.example.composetest.numberFormat
import com.example.composetest.ui.theme.White

@Composable
fun BusinessSectorList(businessSectors: List<BusinessSectorWithCompanies>) {
    businessSectors.forEach {
        BusinessSectorCard(it)
        Spacer(Modifier.padding(10.dp))
    }
}

@Composable
fun BusinessSectorCard(businessSector: BusinessSectorWithCompanies) = Card(
    Modifier
        .fillMaxSize()
        .height(110.dp),
    RoundedCornerShape(15),
    Color(parseColor(businessSector.businessSector.color))
) {
    Column(
        Modifier.padding(start = 26.dp),
        Arrangement.Center
    ) {
        Text(
            businessSector.businessSector.name,
            Modifier
                .weight(3F)
                .padding(top = 10.dp),
            White,
            style = MaterialTheme.typography.subtitle1
        )

        Text(
            stringResource(R.string.sector_companies, businessSector.companies.size.toString()),
            Modifier.weight(2F),
            White,
            style = MaterialTheme.typography.body2
        )

        Text(
            stringResource(
                R.string.sector_turnover,
                numberFormat(businessSector.companies.sumOf { it.turnover })
            ),
            Modifier.weight(2F),
            White,
            style = MaterialTheme.typography.body2
        )
    }
}