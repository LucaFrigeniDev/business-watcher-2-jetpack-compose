package com.example.composetest.ui.screens.detailsScreens.groupScreenComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.composetest.R
import com.example.composetest.domain.other.GroupBusinessSector
import com.example.composetest.numberFormat
import com.example.composetest.ui.theme.LightGrey
import com.example.composetest.ui.theme.White

@Composable
fun BusinessSectors(businessSectors: List<GroupBusinessSector>) = Column(
    Modifier
        .fillMaxWidth()
        .background(LightGrey)
) {
    Text(
        stringResource(R.string.sector_presence),
        Modifier.padding(16.dp),
        White,
        style = MaterialTheme.typography.subtitle1
    )
    LazyRow {
        items(businessSectors) { businessSector -> BusinessSectorCard(businessSector) }
    }
    Spacer(Modifier.padding(10.dp))
}

@Composable
fun BusinessSectorCard(businessSector: GroupBusinessSector) = Card(
    Modifier
        .padding(6.dp)
        .height(200.dp)
        .width(160.dp),
    RoundedCornerShape(15.dp),
    Color(android.graphics.Color.parseColor(businessSector.color))
) {
    Column(Modifier.padding(8.dp)) {
        Text(
            businessSector.businessSector,
            Modifier.fillMaxWidth(),
            White,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1
        )

        Spacer(Modifier.padding(6.dp))

        BusinessSectorStatRow(
            R.drawable.ic_baseline_monetization_on_24,
            stringResource(R.string.euro, numberFormat(businessSector.turnover.toInt()))
        )

        BusinessSectorStatRow(
            R.drawable.ic_baseline_factory_24,
            stringResource(R.string.group_companies, businessSector.companies)
        )

        BusinessSectorStatRow(
            R.drawable.ic_baseline_tag_24,
            stringResource(R.string.rank, businessSector.rank)
        )

        BusinessSectorStatRow(
            R.drawable.ic_baseline_percent_24,
            stringResource(R.string.share, businessSector.share)
        )
    }
}

@Composable
fun BusinessSectorStatRow(iconId: Int, text: String) {
    Row {
        Icon(
            painterResource(iconId),
            "",
            Modifier.size(30.dp),
            White
        )

        Spacer(Modifier.padding(4.dp))

        Text(
            text,
            Modifier.padding(top = 5.dp),
            White,
            style = MaterialTheme.typography.body2
        )
    }
    Spacer(Modifier.padding(4.dp))
}