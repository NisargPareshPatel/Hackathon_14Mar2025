package com.example.urhacks25.ui.auth_flow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.urhacks25.components.auth_flow.sign_in.SignInComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignIn(
    component: SignInComponent
) {
    val isLoading by component.isLoading.subscribeAsState()
    val canContinue by component.canContinue.subscribeAsState()
    val username by component.username.subscribeAsState()
    val password by component.password.subscribeAsState()
    val asStore by component.signAsStore.subscribeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Sign in")
                }, navigationIcon = {
                    IconButton(onClick = component::onBackPressed) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }, bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Button(
                        enabled = canContinue,
                        onClick = component::dispatchAuthorization,
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        Text("Sign in")
                    }
                }
            }
        }
    ) { padding ->
        Box(
            Modifier
                .padding(padding)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Sign in as: ",
                        modifier = Modifier.weight(1f)
                    )

                    SingleChoiceSegmentedButtonRow {
                        SegmentedButton(
                            selected = asStore.not(),
                            onClick = { component.setSignAsStore(false) },
                            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
                        ) {
                            Text("Buyer")
                        }

                        SegmentedButton(
                            selected = asStore,
                            onClick = { component.setSignAsStore(true) },
                            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
                        ) {
                            Text("Store")
                        }
                    }
                }

                OutlinedTextField(
                    value = username,
                    onValueChange = component::setUsername,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Email address")
                    }, keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    )
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = component::setPassword,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Password")
                    }, keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ), visualTransformation = PasswordVisualTransformation()
                )
            }
        }
    }
}