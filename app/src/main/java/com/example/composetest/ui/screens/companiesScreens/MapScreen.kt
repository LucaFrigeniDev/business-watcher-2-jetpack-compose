package com.example.composetest.ui.screens.companiesScreens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composetest.MainActivity
import com.example.composetest.R.drawable
import com.example.composetest.displayScreen
import com.example.composetest.findActivity
import com.example.composetest.ui.ROUTE_MAP
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MapScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel = hiltViewModel<CompaniesViewModel>()
    val scope = rememberCoroutineScope()

    displayScreen = ROUTE_MAP

    viewModel.getUserLocation(context)

    val userLocation by viewModel.userLocation.collectAsState()
    val companies by viewModel.companies.collectAsState(emptyList())

    val cameraPositionState = rememberCameraPositionState {}

    cameraPositionState.position = CameraPosition.fromLatLngZoom(userLocation, 10f)

    GoogleMap(
        Modifier.fillMaxSize(),
        cameraPositionState,
        uiSettings = MapUiSettings(zoomControlsEnabled = false)
    ) {
        companies.forEach {
            Marker(
                LatLng(it.latitude, it.longitude),
                title = it.name,
                snippet = it.city,
                icon = viewModel.createMarker(context, it.businessSectorId),
                onInfoWindowClick = {
                    scope.launch(Dispatchers.Main) {
                        navController.navigate("company?id=${it.id}")
                    }
                }
            )
        }
    }

    Column(
        Modifier.fillMaxSize(),
        Arrangement.Bottom,
        Alignment.CenterHorizontally
    ) {
        GeoLocationFAB(context, cameraPositionState, userLocation, viewModel)
        Spacer(Modifier.padding(bottom = 90.dp))
    }
}

@Composable
fun GeoLocationFAB(
    context: Context,
    cameraPositionState: CameraPositionState,
    userLocation: LatLng,
    viewModel: CompaniesViewModel
) = FloatingActionButton(
    {
        context.findActivity()?.requestPermission()
        viewModel.getUserLocation(context)
        cameraPositionState.position = CameraPosition.fromLatLngZoom(userLocation, 10f)
    },
    Modifier.size(40.dp)
) {
    Icon(painterResource(drawable.ic_baseline_my_location_24), "")
}
