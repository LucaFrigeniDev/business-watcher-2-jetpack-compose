package com.example.composetest

fun setPhotoUrl(url: String): String {
    return "https://firebasestorage.googleapis.com/v0/b/businesswatcher-compose.appspot.com/o/$url?alt=media&token=309e9592-9769-4c5c-9e25-089bf960d35d"
}