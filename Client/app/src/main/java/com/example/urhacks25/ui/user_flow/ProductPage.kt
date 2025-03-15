package com.example.urhacks25.ui.user_flow

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.urhacks25.components.user_flow.product_page.UserProductPageComponent
import java.util.TimeZone
import kotlin.jvm.optionals.getOrNull

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductPage(
    component: UserProductPageComponent
) {
    val isLoading by component.isLoading.subscribeAsState()
    val item by component.item.subscribeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(item.getOrNull()?.name ?: "Product Information")
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
                    val price = item.getOrNull()?.price

                    Button(
                        onClick = component::requestBooking,
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = null)
                            Text("Purchase")

                            Spacer(Modifier.weight(1f))

                            Row(
                                Modifier
                                    .clip(MaterialTheme.shapes.medium)
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    price.toString(),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text("CA$", color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }
    ) { padding ->
        if (item.isEmpty) {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            val item = item.get()

            LazyColumn(contentPadding = padding) {
                item {
                    AsyncImage(
                        model = item.photoUrl,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(MaterialTheme.shapes.medium)
                    )
                }

                item {
                    ListItem(
                        leadingContent = {
                            Icon(Icons.Default.TextFields, contentDescription = null)
                        }, headlineContent = {
                            Text("Name")
                        }, supportingContent = {
                            Text(item.name)
                        }
                    )
                }

                item {
                    val ctx = LocalContext.current

                    val pDate = remember(item.expiry) {
                        android.text.format.DateFormat.getDateFormat(ctx)
                            .also { it.timeZone = TimeZone.getTimeZone("UTC") }
                            .format(item.expiry.toEpochMilliseconds()).toString()
                    }

                    ListItem(
                        leadingContent = {
                            Icon(Icons.Default.DateRange, contentDescription = null)
                        }, headlineContent = {
                            Text("Expires at")
                        }, supportingContent = {
                            Text(pDate)
                        }
                    )
                }

                item {
                    ListItem(
                        leadingContent = {
                            Icon(Icons.Default.Map, contentDescription = null)
                        }, headlineContent = {
                            Text("Open in Maps")
                        }, colors = ListItemDefaults.colors(
                            leadingIconColor = MaterialTheme.colorScheme.primary,
                            headlineColor = MaterialTheme.colorScheme.primary,
                        ), modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            }
                    )
                }
            }
        }
    }
}