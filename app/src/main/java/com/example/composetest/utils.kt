package com.example.composetest

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun setPhotoUrl(url: String): String {
    return "https://firebasestorage.googleapis.com/v0/b/businesswatcher-compose.appspot.com/o/$url?alt=media&token=309e9592-9769-4c5c-9e25-089bf960d35d"
}

var displayScreen = ""

fun numberFormat(turnover: Int?) = if (turnover != null) {
    DecimalFormat("###,###", DecimalFormatSymbols().apply {
        groupingSeparator = ' '
    }).format(turnover).toString()
} else
    "0"

var location = LatLng(0.0, 0.0)

fun Context.findActivity(): MainActivity? = when (this) {
    is MainActivity -> this
    else -> null
}

fun Context.toast(message: Int) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Context.isConnectedToTheNetwork(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return connectivityManager.activeNetwork != null
}