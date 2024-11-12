package com.example.beerapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.beerapp.composables.PrimaryButton
import com.example.beerapp.composables.PrimaryOutlinedTextField
import com.google.firebase.auth.FirebaseUser

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Authentication(
    modifier: Modifier = Modifier,
    user: FirebaseUser? = null,
    message: String = "",
    signIn: (email: String, password: String) -> Unit = { _, _ -> },
    register: (email: String, password: String) -> Unit = { _, _ ->},
    navigateToNextScreen: () -> Unit = {}
) {
    var isLoading by remember { mutableStateOf(false) }
    if (user != null && !isLoading) {
        LaunchedEffect(Unit) {
            isLoading = true
            navigateToNextScreen()
        }

    }
    val emailStart = ""
    val passwordStart = ""
    var email by remember { mutableStateOf(emailStart) }
    var password by remember { mutableStateOf(passwordStart) }
    var emailIsError by remember { mutableStateOf(false) }
    var passwordIsError by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }



        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.Center)
        {
            if (user != null) {
                Text("Welcome ${user.email ?: "unknown"}")
            }
            PrimaryOutlinedTextField(
                modifier = modifier,
                label = "Email",
                value = email,
                keyboardType = KeyboardType.Email,
                tag = "email",
                onValueChange = {
                    it: String ->
                    email = it
                },
                isError = emailIsError,
                visible = true
            )
            if (emailIsError) {
                Text("Invalid email", color = MaterialTheme.colorScheme.error)
            }
            PrimaryOutlinedTextField(
                modifier = modifier,
                value = password,
                onValueChange = { password = it },
                label = "Password",
                tag = "password",
                keyboardType = KeyboardType.Password,
                isError = passwordIsError,
                visible = false
            )

            if (passwordIsError) {
                Text("Invalid password", color = MaterialTheme.colorScheme.error)
            }
            if (message.isNotEmpty()) {
                Text(message, color = MaterialTheme.colorScheme.error)
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                PrimaryButton(
                    onClick = {
                        email = email.trim()
                        password = password.trim()
                        if (email.isEmpty() || !validateEmail(email))
                        {
                            emailIsError = true
                            return@PrimaryButton
                        } else {
                            emailIsError = false
                        }
                        if (password.isEmpty()) {
                            passwordIsError = true
                            return@PrimaryButton
                        } else {
                            passwordIsError = false
                        }
                        signIn(email, password)
                    }, label = "SIGN IN", modifier = modifier.fillMaxWidth()
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                PrimaryButton (
                    onClick = {
                        email = email.trim()
                        password = password.trim()
                        if (email.isEmpty() || !validateEmail(email))
                        {
                            emailIsError = true
                            return@PrimaryButton
                        } else {
                            emailIsError = false
                        }
                        if (password.isEmpty()) {
                            passwordIsError = true
                            return@PrimaryButton
                        } else {
                            passwordIsError = false
                        }
                    register(email, password)
                },
                    modifier = Modifier.fillMaxWidth(),
                    label = "REGISTER"
                )
            }
        }

}

private fun validateEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Preview(showBackground = true)
@Composable
fun AuthPreview() {
    Authentication()
}