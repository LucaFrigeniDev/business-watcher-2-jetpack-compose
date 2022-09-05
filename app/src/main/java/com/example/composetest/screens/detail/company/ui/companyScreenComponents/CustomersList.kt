package com.example.composetest.screens.detail.company.ui.companyScreenComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composetest.R
import com.example.composetest.screens.detail.company.domain.Customer
import com.example.composetest.theme.*
import com.example.composetest.base.EmptyScreen
import com.example.composetest.screens.detail.company.ui.CompanyDetailViewModel

@Composable
fun Customers(customers: List<Customer>, viewModel: CompanyDetailViewModel) = Column(
    Modifier
        .fillMaxWidth()
        .background(LightGrey), horizontalAlignment = Alignment.End
) {

    AddCustomerButton(viewModel)

    Column(Modifier.fillMaxWidth()) {
        Text(
            stringResource(R.string.customer_title),
            Modifier.padding(horizontal = 16.dp),
            color = White,
            style = MaterialTheme.typography.subtitle1
        )

        Spacer(Modifier.padding(10.dp))

        if (customers.isEmpty()) {
            EmptyScreen(
                stringResource(R.string.empty_customer_list),
                White,
                Modifier.size(200.dp)
            )

            Spacer(Modifier.padding(10.dp))
        } else {
            Column(standardColumnModifier) {
                for (customer in customers)
                    CustomerCard(customer)
            }
        }
    }
}

@Composable
fun CustomerCard(customer: Customer) {
    Card(
        Modifier.fillMaxWidth(),
        RoundedCornerShape(10),
        Grey
    ) {
        Column(Modifier.fillMaxSize()) {
            Text(
                customer.name,
                Modifier.padding(16.dp),
                Color.White,
                style = MaterialTheme.typography.body1
            )

            Text(
                customer.description,
                Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                color = Color.White
            )
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp), horizontalAlignment = Alignment.End
        ) {
            FloatingActionButton(
                { /*TODO*/ },
                Modifier.size(30.dp)
            ) {
                Icon(
                    Icons.Default.Edit,
                    "",
                    Modifier.size(18.dp)
                )
            }
        }
    }
    Spacer(Modifier.padding(6.dp))
}

@Composable
fun AddCustomerButton(viewModel: CompanyDetailViewModel) {
    val openDialog = rememberSaveable { mutableStateOf(false) }

    Button(
        { openDialog.value = true },
        Modifier.padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = DarkGrey)
    ) {
        Icon(
            Icons.Default.Add,
            "",
            Modifier.padding(horizontal = 4.dp),
            White
        )

        Text(
            stringResource(R.string.add_customer),
            color = White,
            style = MaterialTheme.typography.button
        )
    }

    if (openDialog.value)
        AddCustomerDialog(openDialog, viewModel)
}