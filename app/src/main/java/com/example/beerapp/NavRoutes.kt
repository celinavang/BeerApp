package com.example.beerapp

sealed class NavRoutes(val route: String) {
    data object Authentication : NavRoutes("authentication")
    data object BeerList : NavRoutes("beerList")
    data object BeerInfo : NavRoutes("beerInfo")
    data object AddBeer : NavRoutes("addBeer")
    data object EditBeer : NavRoutes("editBeer")
}