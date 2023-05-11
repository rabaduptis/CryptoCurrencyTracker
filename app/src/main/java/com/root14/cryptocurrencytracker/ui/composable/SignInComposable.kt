package com.root14.cryptocurrencytracker.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.root14.cryptocurrencytracker.ui.theme.DarkBlack

/**
 * Created by ilkay on 11,May, 2023
 */

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SignInComposable() {

    var email = ""
    var password = ""
    var confirmPassword = ""

    val outlinedTextFieldColor = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = White,
        unfocusedBorderColor = White,
        textColor = White
    )

    Surface(color = DarkBlack) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {


            Text(
                text = "Sign In", style = MaterialTheme.typography.bodyMedium, color = White
            )
            OutlinedTextField(
                value = "email",
                onValueChange = { email = it },
                label = { Text("E-mail", color = White) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier.fillMaxWidth(),
                colors = outlinedTextFieldColor
            )

            OutlinedTextField(
                value = "password",
                onValueChange = { password = it },
                label = { Text("Password", color = White) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier.fillMaxWidth(),
                colors = outlinedTextFieldColor
            )

            OutlinedTextField(
                value = "confirmPassword",
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password", color = White) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier.fillMaxWidth(),
                colors = outlinedTextFieldColor
            )

            Button(
                onClick = { /* sign in button */ }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign In")
            }
        }
    }
}