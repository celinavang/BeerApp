package com.example.beerapp

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.beerapp.composables.NavBar
import com.example.beerapp.model.Beer
import com.example.beerapp.model.BeerViewModel
import com.example.beerapp.screens.AddBeer
import com.example.beerapp.screens.Authentication
import com.example.beerapp.screens.BeerInfo
import com.example.beerapp.screens.BeerList
import com.example.beerapp.screens.EditBeer
import com.example.beerapp.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme(
                content = {
                    Surface(
                        color = AppTheme.colorScheme.background,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        MainScreen()
                    }
                }
            )

        }

    }

}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val authenticationViewModel: AuthenticationViewModel = viewModel()
    val beerViewModel: BeerViewModel = viewModel()
    val beers = beerViewModel.beers.value


    NavHost(
        navController = navController,
        startDestination = NavRoutes.Authentication.route
    ) {
        composable(NavRoutes.Authentication.route) {
            Authentication(
                user = authenticationViewModel.user,
                message = authenticationViewModel.message,
                signIn = { email: String, password: String ->
                    authenticationViewModel.signIn(
                        email,
                        password
                    )
                },
                register = { email: String, password: String ->
                    authenticationViewModel.register(
                        email,
                        password
                    )
                },
                navigateToNextScreen = { navController.navigate(NavRoutes.BeerList.route) }
            )
        }
        composable(NavRoutes.BeerList.route) {
            NavBar(
                modifier = modifier,
                signOut = { authenticationViewModel.signOut() },
                navigateToAdd = { navController.navigate(NavRoutes.AddBeer.route) },
                navigateToList = {navController.navigate(NavRoutes.BeerList.route)},
                content = {
                    if (authenticationViewModel.user == null) {
                        navController.popBackStack(
                            NavRoutes.Authentication.route,
                            inclusive = false
                        )
                    } else {
                        BeerList(
                            user = authenticationViewModel.user,
                            navigateToBeer = { beer -> navController.navigate(NavRoutes.BeerInfo.route + "/${beer.id}") },
                            viewModel = beerViewModel,
                            navigateToEditBeer = { beer -> navController.navigate(NavRoutes.EditBeer.route + "/${beer.id}")}
                        )
                    }
                }
            )
        }
        composable(
            NavRoutes.BeerInfo.route + "/{beerId}",
            arguments = listOf(navArgument("beerId") { type = NavType.IntType })
        ) { backstackEntry ->
            val beerId = backstackEntry.arguments?.getInt("beerId")
            val beer = beers.find { it.id == beerId }
            if (beer != null) {
                NavBar(
                    modifier = modifier,
                    signOut = { authenticationViewModel.signOut() },
                    navigateToAdd = { navController.navigate(NavRoutes.AddBeer.route) },
                    navigateToList = {navController.navigate(NavRoutes.BeerList.route)},
                    content = {
                        if (authenticationViewModel.user == null) {
                            navController.popBackStack(
                                NavRoutes.Authentication.route,
                                inclusive = false
                            )
                        } else {
                            BeerInfo(
                                modifier = modifier,
                                beer = beer,
                                navigateToEdit = {
                                    navController.navigate(NavRoutes.EditBeer.route + "/${beer.id}")
                                },
                                deleteBeer = { id: Int ->
                                    beerViewModel.delete(id)
                                    navController.navigate(NavRoutes.BeerList.route)
                                },
                                nextBeer = {
                                    val nextBeer: Beer? = beerViewModel.beers.value.getOrNull(beerViewModel.beers.value.indexOf(beer)+1)
                                    if (nextBeer != null){
                                        navController.navigate(NavRoutes.BeerInfo.route + "/${nextBeer.id}")
                                    }

                                },
                                prevBeer = {
                                    val nextBeer: Beer? = beerViewModel.beers.value.getOrNull(beerViewModel.beers.value.indexOf(beer)-1)
                                    if (nextBeer != null){
                                        navController.navigate(NavRoutes.BeerInfo.route + "/${nextBeer.id}")
                                    }
                                }
                            )
                        }
                    }
                )
            }

        }
        composable(NavRoutes.AddBeer.route) {
            NavBar(
                modifier = modifier,
                signOut = { authenticationViewModel.signOut() },
                navigateToAdd = { navController.navigate(NavRoutes.AddBeer.route) },
                navigateToList = {navController.navigate(NavRoutes.BeerList.route)},
                content = {
                    if (authenticationViewModel.user == null) {
                        navController.popBackStack(
                            NavRoutes.Authentication.route,
                            inclusive = false
                        )
                    } else {
                        AddBeer(
                            user = authenticationViewModel.user,
                            addBeer = { beer: Beer ->
                                beerViewModel.add(beer)
                                navController.navigate((NavRoutes.BeerList.route))
                            }
                        )
                    }
                }
            )
        }
        composable(
            NavRoutes.EditBeer.route + "/{beerId}",
            arguments = listOf(navArgument("beerId") { type = NavType.IntType })
        ) { backstackEntry ->
            val beerId = backstackEntry.arguments?.getInt("beerId")
            val beer = beers.find { it.id == beerId } ?: null
            if (beer != null) {
                NavBar(
                    modifier = modifier,
                    signOut = { authenticationViewModel.signOut() },
                    navigateToAdd = { navController.navigate(NavRoutes.AddBeer.route) },
                    navigateToList = {navController.navigate(NavRoutes.BeerList.route)},
                    content = {
                        if (authenticationViewModel.user == null) {
                            navController.popBackStack(
                                NavRoutes.Authentication.route,
                                inclusive = false
                            )
                        } else {
                            EditBeer(
                                modifier = modifier,
                                beer = beer,
                                editBeer = { beer: Beer ->
                                    Log.d("APPLE", beer.toString())
                                    beerViewModel.update(id = beer.id, beer = beer)
                                    navController.navigate(NavRoutes.BeerList.route)
                                }
                            )
                        }
                    }
                )
            }
        }
    }
}

