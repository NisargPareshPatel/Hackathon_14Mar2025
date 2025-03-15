package com.example.urhacks25.ui.user_flow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.urhacks25.components.user_flow.store.UserStoreComponent
import com.example.urhacks25.ui.util.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserStore(component: UserStoreComponent) {
    val isLoading by component.isLoading.subscribeAsState()
    val isRefreshing by component.isRefreshing.subscribeAsState()
    val items by component.items.subscribeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(component.storeName)
                }, navigationIcon = {
                    IconButton(onClick = component::onBackPressed) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            PullToRefreshBox(isRefreshing = isRefreshing, onRefresh = component::refresh, modifier = Modifier.padding(padding)) {
                LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(items) { item ->
                        ProductCard(
                            item = item,
                            canGift = false,
                            modifier = Modifier.clickable {
                                component.onProductClicked(productId = item.id!!)
                            }
                        )
                    }
                }
            }
        }
    }
}