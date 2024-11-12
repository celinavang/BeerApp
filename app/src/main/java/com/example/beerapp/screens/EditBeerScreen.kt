package com.example.beerapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.beerapp.composables.PrimaryButton
import com.example.beerapp.composables.PrimaryOutlinedTextField
import com.example.beerapp.model.Beer
import com.example.beerapp.ui.theme.AppTheme

@Composable
fun EditBeer(
    modifier: Modifier = Modifier,
    beer: Beer,
    editBeer: (Beer) -> Unit = {}
) {
    val id by rememberSaveable { mutableIntStateOf(beer.id) }
    val beerUser by rememberSaveable { mutableStateOf(beer.user) }
    var name by rememberSaveable { mutableStateOf(beer.name) }
    var brewery by rememberSaveable { mutableStateOf(beer.brewery) }
    var style by rememberSaveable { mutableStateOf(beer.style) }
    var abv by rememberSaveable { mutableStateOf(beer.abv.toString()) }
    var volume by rememberSaveable { mutableStateOf(beer.volume.toString()) }
    var howMany by rememberSaveable { mutableStateOf(beer.howMany.toString()) }

    var errorType by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier.padding(24.dp)) {
        if (errorType != "") {
            when {
                errorType.contains("name") ->
                    {
                    Text("Error in name", color = AppTheme.colorScheme.warning)
                    }
                errorType.contains("brewery") ->
                {
                    Text("Error in brewery", color = AppTheme.colorScheme.warning)
                }
                errorType.contains("style") ->
                {
                    Text("Error in style", color = AppTheme.colorScheme.warning)
                }
                errorType.contains("abv") ->
                {
                    Text("Error in abv", color = AppTheme.colorScheme.warning)
                }
                errorType.contains("volume") ->
                {
                    Text("Error in volume", color = AppTheme.colorScheme.warning)
                }
            }
        }
        Row {
            PrimaryOutlinedTextField(
                value = name,
                keyboardType = KeyboardType.Text,
                onValueChange = { name = it },
                label = "Name",
                isError = if(errorType.contains("name")) true else false
            )
        }
        Row {
            PrimaryOutlinedTextField(
                value = brewery,
                keyboardType = KeyboardType.Text,
                onValueChange = { brewery = it },
                label = "Brewery",
                isError = if(errorType.contains("brewery")) true else false
            )
        }
        Row {
            PrimaryOutlinedTextField(
                value = style,
                keyboardType = KeyboardType.Text,
                onValueChange = { style = it },
                label = "style",
                isError = if(errorType.contains("style")) true else false
            )
        }
        Row {
            PrimaryOutlinedTextField(
                value = abv,
                keyboardType = KeyboardType.Number,
                onValueChange = { abv = it },
                label = "ABV",
                isError = if(errorType.contains("abv")) true else false
            )
        }
        Row {
            PrimaryOutlinedTextField(
                value = volume,
                keyboardType = KeyboardType.Number,
                onValueChange = { volume = it },
                label = "Volume",
                isError = if(errorType.contains("volume")) true else false
            )
        }
        Row {
            PrimaryOutlinedTextField(
                value = howMany,
                keyboardType = KeyboardType.Number,
                onValueChange = { howMany = it },
                label = "Amount",
                isError = if(errorType.contains("amount")) true else false
            )
        }
        Row {
            PrimaryButton(label = "UPDATE",
                onClick = {
                    errorType = ""

                    if (name == ""){
                        errorType += "name"
                    }
                    if (brewery == ""){
                        errorType += "brewery"
                    }
                    if (style == ""){
                        errorType += "style"
                    }
                    if (abv == ""){
                        errorType += "abv"
                    }
                    if (volume == ""){
                        errorType += "volume"
                    }
                    if (howMany == ""){
                        errorType += "amount"
                    }
                    if(errorType == "") {
                        val updatedBeer = Beer(
                            id = id,
                            user = beerUser,
                            brewery = brewery,
                            name = name,
                            style = style,
                            pictureUrl = "",
                            abv = abv.toDouble(),
                            volume = volume.toDouble(),
                            howMany = howMany.toInt()
                        )
                        editBeer(updatedBeer)
                    }

            },
                modifier = modifier.fillMaxWidth()
                    .padding(top = 20.dp)
            )
        }
    }
}


