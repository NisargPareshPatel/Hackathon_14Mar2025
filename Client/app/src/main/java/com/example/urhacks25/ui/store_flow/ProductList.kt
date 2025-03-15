package com.example.urhacks25.ui.store_flow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.urhacks25.components.store_flow.product_list.StoreProductListComponent
import com.example.urhacks25.ui.util.BookedProductCard
import com.example.urhacks25.ui.util.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductList(component: StoreProductListComponent) {
    val isLoading by component.isLoading.subscribeAsState()
    val isRefreshing by component.isRefreshing.subscribeAsState()

    val items by component.items.subscribeAsState()
    val itemsBooked by component.itemsBooked.subscribeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Products")
                }, actions = {
                    IconButton(onClick = component::onLogoutClicked) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null)
                    }
                }
            )
        }, floatingActionButton = {
            FloatingActionButton(onClick = component::onCreateClicked) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { padding ->
        if (isLoading) {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            PullToRefreshBox(isRefreshing = isRefreshing, onRefresh = component::refresh, modifier = Modifier.padding(padding)) {
                LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    if (itemsBooked.isNotEmpty()) {
                        item {
                            Text("Booked items", style = MaterialTheme.typography.labelLarge)
                        }

                        items(itemsBooked) { item ->
                            BookedProductCard(
                                item = item.item,
                                user = item.user,
                                modifier = Modifier
                            )
                        }

                        item {
                            Text("Available items", style = MaterialTheme.typography.labelLarge)
                        }
                    }

                    items(items) { item ->
                        ProductCard(
                            item = item,
                            canGift = true,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}