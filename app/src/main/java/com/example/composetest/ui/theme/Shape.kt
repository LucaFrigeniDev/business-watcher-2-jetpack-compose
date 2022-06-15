package com.example.composetest.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val standardColumnModifier = Modifier
    .fillMaxSize()
    .padding(16.dp)

val modifierFullLength = Modifier
    .fillMaxWidth()
    .padding(vertical = 2.dp)

val modifierSmallLength = Modifier
    .width(130.dp)
    .padding(vertical = 2.dp)

val modifierHeight = Modifier
    .fillMaxWidth()
    .height(200.dp)
    .padding(vertical = 4.dp)

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)