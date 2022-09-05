package com.example.composetest.screens.map.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color.parseColor
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.R
import com.example.composetest.location
import com.example.composetest.screens.map.domain.CompanyMapMarker
import com.example.composetest.screens.map.domain.GetCompanyMapMarkerUseCase
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getCompanyMapMarkerUseCase: GetCompanyMapMarkerUseCase
) : ViewModel() {

    var markerList: StateFlow<List<CompanyMapMarker>> = getCompanyMapMarkerUseCase.markerList

    init {
        viewModelScope.launch {
            getCompanyMapMarkerUseCase.set()
        }
        viewModelScope.launch {
            getCompanyMapMarkerUseCase.execute()
        }
    }

    private val _userLocation = MutableStateFlow(location)
    val userLocation: StateFlow<LatLng> = _userLocation

    fun getUserLocation(context: Context) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) -> {
                viewModelScope.launch {
                    LocationServices.getFusedLocationProviderClient(context)
                        .lastLocation.addOnCompleteListener { task ->
                            if (task.isSuccessful && task.result != null) {
                                location = LatLng(task.result.latitude, task.result.longitude)
                                _userLocation.value = location
                            }
                        }
                }
            }
        }
    }

    fun createMarker(context: Context, color: String): BitmapDescriptor {

        val background = ContextCompat.getDrawable(context, R.drawable.ic_baseline_location_on_24)
        val wrappedDrawable = DrawableCompat.wrap(background!!)
        DrawableCompat.setTint(wrappedDrawable, parseColor(color))

        background.setBounds(0, 0, background.intrinsicWidth, background.intrinsicHeight)

        val bitmap = Bitmap.createBitmap(
            background.intrinsicWidth,
            background.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        background.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}