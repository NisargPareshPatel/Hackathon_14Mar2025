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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.example.urhacks25.components.auth_flow.landing.LandingComponent
import com.example.urhacks25.components.auth_flow.register.RegisterComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(
    component: RegisterComponent
) {
    val isLoading by component.isLoading.subscribeAsState()
    val canContinue by component.canContinue.subscribeAsState()
    val username by component.username.subscribeAsState()
    val password by component.password.subscribeAsState()
    val cPassword by component.confirmPassword.subscribeAsState()
    val sLocation by component.location.subscribeAsState()
    val sName by component.storeName.subscribeAsState()
    val fName by component.firstName.subscribeAsState()
    val lName by component.lastName.subscribeAsState()
    val uPhone by component.phoneNumber.subscribeAsState()
    val asStore by component.registerAsStore.subscribeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Create an account")
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
                        Text("Continue")
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
                modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "I am a: ",
                        modifier = Modifier.weight(1f)
                    )

                    SingleChoiceSegmentedButtonRow {
                        SegmentedButton(
                            selected = asStore.not(),
                            onClick = { component.setRegisterAsStore(false) },
                            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
                        ) {
                            Text("Buyer")
                        }

                        SegmentedButton(
                            selected = asStore,
                            onClick = { component.setRegisterAsStore(true) },
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

                OutlinedTextField(
                    value = cPassword,
                    onValueChange = component::setConfirmPassword,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Confirm password")
                    }, keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ), visualTransformation = PasswordVisualTransformation()
                )

                if (asStore) {
                    OutlinedTextField(
                        value = sName,
                        onValueChange = component::setStoreName,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Store Name")
                        }, keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text
                        )
                    )
                } else {
                    OutlinedTextField(
                        value = fName,
                        onValueChange = component::setFirstName,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("First name")
                        }, keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text
                        )
                    )

                    OutlinedTextField(
                        value = lName,
                        onValueChange = component::setLastName,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Last name")
                        }, keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text
                        )
                    )

                    OutlinedTextField(
                        value = uPhone,
                        onValueChange = component::setPhoneNumber,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Phone number")
                        }, keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone
                        )
                    )
                }
            }
        }
    }
}