package com.example.beerapp.screens

import android.content.res.Configuration
import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beerapp.R
import com.example.beerapp.composables.PrimaryButton
import com.example.beerapp.composables.SearchTextField
import com.example.beerapp.model.Beer
import com.example.beerapp.model.BeerViewModel
import com.example.beerapp.ui.theme.AppTheme
import com.google.firebase.auth.FirebaseUser

@Composable
fun BeerList(
    modifier: Modifier = Modifier,
    user: FirebaseUser? = null,
    navigateToBeer: (Beer) -> Unit = {},
    navigateToEditBeer: (Beer) -> Unit = {},
    viewModel: BeerViewModel,
) {
    viewModel.getBeers(user)
    val beers = viewModel.beers.value
    val errorMessage = viewModel.errorMessage.value
    var sort: String by rememberSaveable { mutableStateOf("") }
    var filter: String by rememberSaveable { mutableStateOf("") }

    Column {
        Text(errorMessage)

        Row(modifier = modifier.padding(10.dp).testTag("search")) {
            SearchTextField(
                value = filter,
                onClick = { it: String ->
                    filter = it
                },
                tag = "search"
            )
        }
        LazyRow(modifier = modifier.padding(start = 15.dp, end = 15.dp)) {
            item {
                Text("Name", color = Color.White, fontSize = 18.sp, modifier = modifier.clickable {
                    sort = if (sort == "") "n_desc" else ""
                })
                if (sort == "n_desc") {
                    Icon(Icons.Filled.KeyboardArrowDown, "", tint = Color.White)
                }
                else {
                    Icon(Icons.Filled.KeyboardArrowUp, "", tint = Color.White)
                }
            }
            item {
                Text("Brewery", color = Color.White, fontSize = 18.sp, modifier = modifier.clickable {
                    sort = if (sort == "b_asc") "b_desc" else "b_asc"
                })
                if (sort == "b_desc") {
                    Icon(Icons.Filled.KeyboardArrowDown, "", tint = Color.White)
                }
                else {
                    Icon(Icons.Filled.KeyboardArrowUp, "", tint = Color.White)
                }
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(count = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2),
            modifier = modifier.fillMaxSize(),

            ) {

            var beerList: List<Beer> = beers
            when (sort) {
                "" -> beerList = beerList.sortedBy { beer -> beer.name }
                "n_desc" -> beerList = beerList.sortedByDescending { beer -> beer.name }
                "b_asc" -> beerList = beerList.sortedBy { beer -> beer.brewery }
                "b_desc" -> beerList = beerList.sortedByDescending { beer -> beer.brewery }
            }
            if (filter != "") {
                beerList = beerList.filter { beer -> beer.name.contains(filter) or beer.brewery.contains(filter) }
            }
            items(beerList) { beer ->
                BeerRow(
                    beer = beer,
                    selectBeer = { beer: Beer ->
                        navigateToBeer(beer)
                    },
                    editBeer = navigateToEditBeer
                )
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BeerRow(
    modifier: Modifier = Modifier,
    beer: Beer,
    selectBeer: (Beer) -> Unit = {},
    editBeer: (Beer) -> Unit = {}
) {
    Column(modifier = modifier.combinedClickable(
        onClick = {selectBeer(beer)},
        onLongClick = {editBeer(beer)}
    )) {
        Card(
            modifier = modifier
                .padding(10.dp)
                .aspectRatio(1f), colors = CardDefaults.cardColors(
                containerColor = AppTheme.colorScheme.primary
            )
        )
        {
            Text(
                beer.name.toString(), color = AppTheme.colorScheme.onBackground,
                modifier = modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth(), textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Text(
                beer.brewery.toString(), color = AppTheme.colorScheme.onBackground,
                modifier = modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth(), textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
            Text(
                beer.style.toString(), color = AppTheme.colorScheme.onBackground,
                modifier = modifier
                    .fillMaxWidth(), textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
        }

    }
}

@Preview
@Composable
fun CardPreview(modifier: Modifier = Modifier) {
    BeerRow(
        modifier = Modifier.width(150.dp),
        beer = Beer(
            user = "User@email.dk",
            brewery = "Brewery",
            name = "Name",
            style = "Style",
            abv = 1.0,
            volume = 33.0,
            pictureUrl = "",
            howMany = 1
        )
    )
}

@Preview
@Composable
fun ListPreview() {
}