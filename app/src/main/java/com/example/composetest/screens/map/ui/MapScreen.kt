package com.example.composetest.screens.map.ui

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
import com.example.composetest.R.drawable
import com.example.composetest.base.ROUTE_MAP
import com.example.composetest.displayScreen
import com.example.composetest.findActivity
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

@Composable
fun MapScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel = hiltViewModel<MapViewModel>()
    val scope = rememberCoroutineScope()

    displayScreen = ROUTE_MAP

    val companyMapMarkers by viewModel.markerList.collectAsState(emptyList())

    viewModel.getUserLocation(context)
    val userLocation by viewModel.userLocation.collectAsState()

    val cameraPositionState = rememberCameraPositionState {}
    cameraPositionState.position = CameraPosition.fromLatLngZoom(userLocation, 10f)

    GoogleMap(
        Modifier.fillMaxSize(),
        cameraPositionState,
        uiSettings = MapUiSettings(zoomControlsEnabled = false)
    ) {
        companyMapMarkers.forEach { companyMapMarker ->
            Marker(
                companyMapMarker.latLng,
                title = companyMapMarker.name,
                snippet = companyMapMarker.city,
                icon = viewModel.createMarker(context, companyMapMarker.color),
                onInfoWindowClick = {
                    scope.launch {
                        navController.navigate("company?id=${companyMapMarker.id}")
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
    viewModel: MapViewModel
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
