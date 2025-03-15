package com.example.urhacks25.ui.store_flow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.urhacks25.components.store_flow.product_list.StoreProductListComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductList(component: StoreProductListComponent) {
    val isLoading by component.isLoading.subscribeAsState()
    val isRefreshing by component.isRefreshing.subscribeAsState()
    val items by component.items.subscribeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Products")
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
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(items) { item ->
                        Text(item.toString())
                    }
                }
            }
        }
    }
}