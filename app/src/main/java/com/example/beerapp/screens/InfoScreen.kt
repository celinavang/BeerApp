package com.example.beerapp.screens

import android.util.Log
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beerapp.composables.PrimaryButton
import com.example.beerapp.model.Beer
import com.example.beerapp.ui.theme.AppTheme
import kotlin.math.roundToInt

@Composable
fun BeerInfo(
    beer: Beer?,
    modifier: Modifier = Modifier,
    navigateToEdit: (Beer) -> Unit = {},
    deleteBeer: (Int) -> Unit = {},
    nextBeer: () -> Unit = {},
    prevBeer: () -> Unit = {},
    sensitivityFactor: Float = 3f,
    swipeThreshold: Float = 400f
) {

    var offset by remember { mutableFloatStateOf(0f) }
    val density = LocalDensity.current.density
    Column(modifier = modifier
        .padding(24.dp)
        .fillMaxWidth()
        .offset { IntOffset(offset.roundToInt(), 0) }
        .pointerInput(Unit) {
            detectHorizontalDragGestures(onDragEnd = {
                offset = 0f
            }) { change, dragAmount ->
                offset += (dragAmount / density) * sensitivityFactor
                when {
                    offset > swipeThreshold -> {
                        Log.d("APPLE", "Dismissed")
                        prevBeer()
                    }

                    offset < -swipeThreshold -> {
                        Log.d("APPLE", "Dismissed also")
                        nextBeer()
                    }
                }
                if (change.positionChange() != Offset.Zero) change.consume()
            }
        }
    ) {
        if (beer != null) {
            Text(
                text = beer.name.toUpperCase(),
                color = Color.White,
                fontSize = 30.sp
            )
            HorizontalDivider( modifier = modifier.padding(top = 10.dp, bottom = 10.dp))
            Text("Brewery: " + beer.brewery,
                color = Color.White,
                fontSize = 20.sp
            )
            Text("Style: " + beer.style,
                color = Color.White,
                fontSize = 20.sp
            )
            Text("ABV: " + beer.abv.toString(),
                color = Color.White,
                fontSize = 20.sp
            )
            Text("Volume: " + beer.volume.toString(),
                color = Color.White,
                fontSize = 20.sp
            )
            Text("Amount: " + beer.howMany.toString(),
                color = Color.White,
                fontSize = 20.sp
            )
            PrimaryButton(
                label = "EDIT",
                onClick = {
                    navigateToEdit(beer)
                },
                modifier =
                modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
            PrimaryButton(
                label = "DELETE",
                onClick = { deleteBeer(beer.id) },
                modifier =
                    modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                color = AppTheme.colorScheme.warning
            )
        } else {
            Text("beer null", color = Color.White)
        }
    }


}