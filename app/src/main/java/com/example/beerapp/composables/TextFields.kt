package com.example.beerapp.composables


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.beerapp.ui.theme.AppTheme

@Composable
fun PrimaryOutlinedTextField(
    modifier: Modifier = Modifier,
    label: String = "label",
    value: String = "value",
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (it: String) -> Unit = {},
    isError: Boolean = false,
    visible: Boolean = true,
    tag: String = ""
    ) {
    var valueNow by remember { mutableStateOf(value) }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth().testTag(tag),
        value = valueNow,
        onValueChange = {
            valueNow = it
            onValueChange(valueNow)},
        label = { Text(label) },
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedLabelColor = Color.White,
            focusedLabelColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
        ),
        visualTransformation = if(visible) VisualTransformation.None
        else PasswordVisualTransformation()
    )
}

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String = "value",
    onClick: (it: String) -> Unit = {},
    tag: String = ""
){
    var valueNow by remember { mutableStateOf(value) }
    Row(modifier = modifier.height(60.dp)) {
        Column(modifier = modifier.fillMaxWidth()) {
            TextField(
                modifier = modifier.fillMaxWidth()
                    .testTag(tag),
                value = valueNow,
                placeholder = { Text("Search..") },
                onValueChange = {
                    valueNow = it
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = AppTheme.colorScheme.primary,
                    focusedContainerColor = AppTheme.colorScheme.primary,
                    unfocusedTextColor = AppTheme.colorScheme.onPrimary,
                    focusedTextColor = AppTheme.colorScheme.onPrimary,
                ),
                shape = RoundedCornerShape(10.dp),

            )
        }
        Column(modifier =
        modifier
            .fillMaxHeight()
            .aspectRatio(1f)
            .padding(end = 50.dp)){
            IconButton(
                modifier = modifier
                    .padding(end = 40.dp)
                    .zIndex(1f)
                    .fillMaxHeight()
                    .aspectRatio(1f),
                onClick = { onClick(valueNow) },
                ){
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Log out",
                    tint = AppTheme.colorScheme.onPrimary
                )
            }
        }
    }

}

@Preview
@Composable
fun PreviewOutlined(){
    PrimaryOutlinedTextField()
}

@Preview
@Composable
fun PreviewSearchField(){
    AppTheme {
        SearchTextField()
    }

}