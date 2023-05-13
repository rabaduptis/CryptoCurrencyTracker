package com.root14.cryptocurrencytracker.ui.composable

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.root14.cryptocurrencytracker.ui.theme.DarkBlack
import com.root14.cryptocurrencytracker.viewmodel.MainViewModel
import kotlinx.coroutines.launch

/**
 * Created by ilkay on 11,May, 2023
 */

@Composable
fun LoginComposable(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = mainViewModel.signInState.collectAsState(initial = null)


    LaunchedEffect(key1 = state.value?.isSuccess) {
        scope.launch {
            if (state.value?.isSuccess?.isNotEmpty() == true) {
                val success = state.value?.isSuccess
                Toast.makeText(context, "${success}", Toast.LENGTH_LONG).show()
                navController.navigate("listAllCoin_destination")
            }
        }
    }

    LaunchedEffect(key1 = state.value?.isError) {
        scope.launch {
            if (state.value?.isError?.isNotEmpty() == true) {
                val error = state.value?.isError
                Toast.makeText(context, "${error}", Toast.LENGTH_LONG).show()
            }
        }
    }

    val outlinedTextFieldColor = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.White,
        unfocusedBorderColor = Color.White,
        cursorColor = Color.White,
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White
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
                text = "Login", style = MaterialTheme.typography.bodyMedium, color = Color.White
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail", color = Color.White) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier.fillMaxWidth(),
                colors = outlinedTextFieldColor
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier.fillMaxWidth(),
                colors = outlinedTextFieldColor
            )

            Button(
                onClick = {
                    scope.launch {
                        mainViewModel.login(email, password)

                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Login")
            }
            Text(color = Color.White,
                text = buildAnnotatedString {
                    append("Don't you ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.Red,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("have")
                    }
                    append(" an account?")
                },
                modifier = Modifier.clickable {
                    navController.navigate("signUp_destination")
                }
            )
        }
    }
}