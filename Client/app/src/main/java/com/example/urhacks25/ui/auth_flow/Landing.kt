package com.example.urhacks25.ui.auth_flow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.urhacks25.components.auth_flow.landing.LandingComponent
import com.example.urhacks25.ui.theme.robotoMonoFontFamily

@Composable
fun Landing(
    component: LandingComponent
) {
    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = component::onSignInClicked,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Text("Sign in")
                }

                OutlinedButton(
                    onClick = component::onSignUpClicked,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Text("Create an account")
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text(
                text = "Welcome to FutureFeast",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = robotoMonoFontFamily,
                modifier = Modifier.padding(16.dp)
            )

            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LazyColumn {
                    item {
                        ListItem(
                            headlineContent = {
                                Text("Great food at great prices")
                            }, leadingContent = {
                                Icon(Icons.Default.LocalOffer, contentDescription = null)
                            }, supportingContent = {
                                Text("Enjoy incredible savings on your favorite food. The only catch is simple - consume it on the same day!")
                            }, colors = ListItemDefaults.colors(
                                leadingIconColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }

                    item {
                        ListItem(
                            headlineContent = {
                                Text("Help the environment")
                            }, leadingContent = {
                                Icon(Icons.Default.Forest, contentDescription = null)
                            }, supportingContent = {
                                Text("Protect the planet by greatly reducing the amount of food waste.")
                            }, colors = ListItemDefaults.colors(
                                leadingIconColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }

                    item {
                        ListItem(
                            headlineContent = {
                                Text("Don't waste the ingredients")
                            }, leadingContent = {
                                Icon(Icons.Default.PointOfSale, contentDescription = null)
                            }, supportingContent = {
                                Text("You can still sell your leftovers and almost expired food instead of throwing it out.\n\nFutureFeast connects you and potential savers in an easiest way possible. We only take 10% fee from each sale.")
                            }, overlineContent = {
                                Text("For store owners")
                            }, colors = ListItemDefaults.colors(
                                leadingIconColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }
            }
        }
    }
}