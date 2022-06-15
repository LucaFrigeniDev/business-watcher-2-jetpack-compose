package com.example.composetest.ui.screens.detailsScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composetest.R
import com.example.composetest.domain.other.Customer
import com.example.composetest.ui.base.EmptyScreen
import com.example.composetest.ui.theme.*

@Composable
fun CompanyDetailScreen(navController: NavController, id: Long) {
    val viewModel = hiltViewModel<CompanyDetailViewModel>()
    viewModel.getCompany(id)

    val company by viewModel.company.collectAsState()
    val businessSectorName by viewModel.businessSectorName.collectAsState()
    val groupName by viewModel.groupName.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        DetailScreenHeader(
            navController,
            company?.logo ?: "",
            company?.name ?: "",
            groupName,
            company?.description ?: "",
            viewModel,
            null
        )

        Spacer(Modifier.padding(2.dp))

        CompanyStats(
            businessSectorName,
            company?.turnover.toString(),
            company?.address + ", " + company?.city + ", " + company?.postalCode
        )

        Spacer(Modifier.padding(6.dp))

        Customers(company?.customers ?: emptyList(), viewModel)
    }
}

@Composable
fun CompanyStats(sector: String, turnover: String, address: String) =
    Column(standardColumnModifier) {
        Row {
            Icon(
                painterResource(R.drawable.ic_baseline_factory_24),
                "",
                Modifier.size(30.dp)
            )

            Spacer(Modifier.padding(4.dp))

            Text(sector, Modifier.padding(top = 4.dp), style = MaterialTheme.typography.body1)
        }

        Spacer(Modifier.padding(4.dp))

        Row {
            Icon(
                painterResource(R.drawable.ic_baseline_monetization_on_24),
                "",
                Modifier.size(30.dp)
            )

            Spacer(Modifier.padding(4.dp))

            Text(
                stringResource(R.string.euro, turnover),
                Modifier.padding(top = 4.dp),
                style = MaterialTheme.typography.body1
            )
        }

        Spacer(Modifier.padding(4.dp))

        Row {
            Icon(Icons.Default.LocationOn, "", Modifier.size(30.dp))

            Spacer(Modifier.padding(4.dp))

            Text(address, Modifier.padding(top = 4.dp), style = MaterialTheme.typography.body1)
        }
    }

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

        if (customers.isEmpty())
            EmptyScreen(
                stringResource(R.string.empty_customer_list),
                White,
                Modifier.size(200.dp)
            )
        else {
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

@Composable
fun AddCustomerDialog(openDialog: MutableState<Boolean>, viewModel: CompanyDetailViewModel) =
    AlertDialog(
        { openDialog.value = false },
        {
            Column {
                Spacer(Modifier.padding(6.dp))
                Text(
                    stringResource(R.string.add_customer),
                    Modifier.fillMaxWidth(),
                    Grey,
                    style = MaterialTheme.typography.subtitle1,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.padding(6.dp))
                CustomerNameTextField(viewModel)
                Spacer(Modifier.padding(4.dp))
                CustomerDescriptionTextField(viewModel)
                Spacer(Modifier.padding(6.dp))
                Row(Modifier.fillMaxSize().padding(end = 16.dp), Arrangement.End) {
                    TextButton({ openDialog.value = false }) {
                        Text(
                            stringResource(R.string.add_sector_dialog_negative_btn),
                            color = Grey,
                            style = MaterialTheme.typography.button
                        )
                    }
                    Spacer(Modifier.padding(4.dp))
                    TextButton(
                        {
                            viewModel.addCustomer()
                            openDialog.value = false
                        },
                        Modifier.background(DarkGrey, RoundedCornerShape(50.dp))
                    ) {
                        Text(
                            text = stringResource(R.string.add_sector_dialog_positive_btn),
                            color = White,
                            style = MaterialTheme.typography.button
                        )
                    }
                }
            }
        },
        Modifier.height(320.dp),
        backgroundColor = White,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
        shape = RoundedCornerShape(16.dp),
    )

@Composable
fun CustomerNameTextField(viewModel: CompanyDetailViewModel) {
    var name by rememberSaveable { mutableStateOf("") }

    TextField(
        name,
        { name = it; viewModel.setCustomerName(it) },
        Modifier.fillMaxWidth().padding(horizontal = 10.dp),
        textStyle = MaterialTheme.typography.body2,
        label = {
            Text(
                stringResource(R.string.name),
                color = White,
                style = MaterialTheme.typography.body1
            )
        },
        colors = TextFieldDefaults.textFieldColors(White, backgroundColor = LightGrey)
    )
}

@Composable
fun CustomerDescriptionTextField(viewModel: CompanyDetailViewModel) {
    var description by rememberSaveable { mutableStateOf("") }

    TextField(
        description,
        { description = it; viewModel.setCustomerDescription(it) },
        Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 10.dp),
        textStyle = MaterialTheme.typography.body2,
        label = {
            Text(
                stringResource(R.string.description),
                color = White,
                style = MaterialTheme.typography.body1
            )
        },
        colors = TextFieldDefaults.textFieldColors(White, backgroundColor = LightGrey)
    )
}



