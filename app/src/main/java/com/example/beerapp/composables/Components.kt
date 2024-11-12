package com.example.beerapp.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.example.beerapp.ui.theme.AppTheme
import com.example.beerapp.ui.theme.BeerAppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(
    modifier: Modifier = Modifier,
    navigateToAdd: () -> Unit = {},
    navigateToList: () -> Unit = {},
    signOut: () -> Unit = {},
    content: @Composable () -> Unit = {}
){
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.colorScheme.primary,
                    titleContentColor = AppTheme.colorScheme.onBackground
                ),
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = {navigateToList()}) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "",
                            tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = navigateToAdd) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Localized description",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = signOut, modifier = Modifier.testTag("logout")) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                            contentDescription = "Log out",
                            tint = Color.White
                        )
                    }
                },
            )
        },
        contentColor = AppTheme.colorScheme.background,
        containerColor = AppTheme.colorScheme.background
    ) { innerPadding ->
        Column(modifier.padding(innerPadding)) {
            content()
        }
    }

}