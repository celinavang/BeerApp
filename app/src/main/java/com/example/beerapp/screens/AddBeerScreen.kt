package com.example.beerapp.screens

import android.util.Log
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.beerapp.composables.PrimaryButton
import com.example.beerapp.composables.PrimaryOutlinedTextField
import com.example.beerapp.model.Beer
import com.example.beerapp.ui.theme.AppTheme
import com.google.firebase.auth.FirebaseUser

@Composable
fun AddBeer(
    modifier: Modifier = Modifier,
    user: FirebaseUser? = null,
    addBeer: (Beer) -> Unit = {}
) {
    var name by rememberSaveable { mutableStateOf("") }
    var brewery by rememberSaveable { mutableStateOf("") }
    var style by rememberSaveable { mutableStateOf("") }
    var abv by rememberSaveable { mutableStateOf("") }
    var volume by rememberSaveable { mutableStateOf("") }
    var howMany by rememberSaveable { mutableStateOf("") }

    var errorType by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier) {
        if (errorType != "") {
            when {
                errorType.contains("name") -> {
                    Text("Error in name", color = AppTheme.colorScheme.warning)
                }

                errorType.contains("brewery") -> {
                    Text("Error in brewery", color = AppTheme.colorScheme.warning)
                }

                errorType.contains("style") -> {
                    Text("Error in style", color = AppTheme.colorScheme.warning)
                }

                errorType.contains("abv") -> {
                    Text("Error in abv", color = AppTheme.colorScheme.warning)
                }

                errorType.contains("volume") -> {
                    Text("Error in volume", color = AppTheme.colorScheme.warning)
                }
            }
        }
        LazyColumn(
            modifier = modifier
                .padding(24.dp)
        )
        {

            item {
                PrimaryOutlinedTextField(
                    modifier = modifier,
                    keyboardType = KeyboardType.Text,
                    onValueChange = { name = it },
                    label = "Name",
                    value = name,
                    isError = if (errorType.contains("name")) true else false
                )
            }
            item {
                PrimaryOutlinedTextField(
                    modifier = modifier,
                    keyboardType = KeyboardType.Text,
                    onValueChange = { brewery = it },
                    label = "Brewery",
                    value = brewery,
                    isError = errorType.contains("brewery")
                )
            }
            item {
                PrimaryOutlinedTextField(
                    modifier = modifier,
                    keyboardType = KeyboardType.Text,
                    onValueChange = { style = it },
                    label = "Style",
                    value = style,
                    isError = errorType.contains("style")
                )
            }
            item {
                PrimaryOutlinedTextField(
                    modifier = modifier,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { abv = it },
                    label = "ABV",
                    value = abv,
                    isError = errorType.contains("abv")
                )
            }
            item {
                PrimaryOutlinedTextField(
                    modifier = modifier,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { volume = it },
                    label = "Volume",
                    value = volume,
                    isError = errorType.contains("volume")
                )
            }
            item {
                PrimaryOutlinedTextField(
                    modifier = modifier,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { howMany = it },
                    label = "Amount",
                    value = howMany,
                    isError = errorType.contains("amount")
                )
            }
            item {
                Row(modifier = modifier.padding(top = 20.dp)) {
                    PrimaryButton(
                        label = "CREATE",
                        modifier = modifier.fillMaxWidth(),
                        onClick = {
                            errorType = ""

                            Log.d("APPLE", abv)
                            if (name == "") {
                                errorType += "name"
                            }
                            if (brewery == "") {
                                errorType += "brewery"
                            }
                            if (style == "") {
                                errorType += "style"
                            }
                            if (abv == "") {
                                errorType += "abv"
                            }
                            if (volume == "") {
                                errorType += "volume"
                            }
                            if (howMany == "") {
                                errorType += "amount"
                            }
                            if (errorType == "") {
                                val beer = Beer(
                                    user = user?.email ?: "",
                                    brewery = brewery,
                                    name = name,
                                    style = style,
                                    pictureUrl = "",
                                    abv = abv.toDouble(),
                                    volume = volume.toDouble(),
                                    howMany = howMany.toInt()
                                )
                                addBeer(beer)
                            }
                        }
                    )
                }
            }
        }
    }
}



