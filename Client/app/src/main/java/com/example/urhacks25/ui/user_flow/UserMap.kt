package com.example.urhacks25.ui.user_flow

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.urhacks25.components.user_flow.map.UserMapComponent
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserMap(
    component: UserMapComponent
) {
    val clusters by component.clusters.subscribeAsState()
    val inDirectoryMode by component.inDirectoryMode.subscribeAsState()
    var alreadyAdjusted by rememberSaveable { mutableStateOf(false) }

    val pagerState = rememberPagerState { 2 }
    val cameraPositionState = rememberCameraPositionState()
    val ctx = LocalContext.current

    LaunchedEffect(alreadyAdjusted) {
        if (alreadyAdjusted.not()) {
            runCatching {
                LocationServices.getFusedLocationProviderClient(ctx).lastLocation.addOnSuccessListener { location ->
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(
                        LatLng(location.latitude, location.longitude),
                        15f
                    )
                }
            }

            alreadyAdjusted = true
        }
    }

    LaunchedEffect(inDirectoryMode) {
        pagerState.animateScrollToPage(if (inDirectoryMode) 1 else 0)
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = inDirectoryMode.not(),
                    onClick = { component.setInDirectoryMode(false) },
                    label = { Text("Map") },
                    icon = { Icon(Icons.Default.Map, contentDescription = null) }
                )

                NavigationBarItem(
                    selected = inDirectoryMode,
                    onClick = { component.setInDirectoryMode(true) },
                    label = { Text("Directory") },
                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) }
                )
            }
        }, contentWindowInsets = WindowInsets(0)
    ) { padding ->
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier.padding(padding)
        ) { index ->
            if (index == 0) {
                Box(Modifier.fillMaxSize()) {
                    GoogleMap(
                        cameraPositionState = cameraPositionState,
                        modifier = Modifier.fillMaxSize(),
                        mapColorScheme = ComposeMapColorScheme.DARK,
                        uiSettings = MapUiSettings(
                            mapToolbarEnabled = true,
                            myLocationButtonEnabled = true,
                            zoomControlsEnabled = true
                        )
                    ) {
                        for (cluster in clusters) {
                            Marker(
                                state = rememberMarkerState(
                                    key = cluster.id,
                                    position = cluster.coordinates
                                ),
                                title = cluster.name,
                                onClick = { marker ->
                                    component.onStoreClicked(
                                        storeId = cluster.id,
                                        storeName = cluster.name
                                    )
                                    true
                                }
                            )
                        }
                    }

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f),
                                        MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f),
                                        Color.Transparent
                                    )
                                )
                            )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .statusBarsPadding()
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(16.dp)
                        ) {
                            Icon(Icons.Default.Search, contentDescription = null)
                            Text("Search...", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            } else {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Directory")
                            }, actions = {
                                IconButton(onClick = component::onLogoutClicked) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.Logout,
                                        contentDescription = null
                                    )
                                }
                            }
                        )
                    }
                ) { padding ->
                    LazyColumn(contentPadding = padding) {
                        items(clusters) { cluster ->
                            Column {
                                ListItem(
                                    headlineContent = {
                                        Text(cluster.name)
                                    }, modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            component.onStoreClicked(
                                                storeId = cluster.id,
                                                storeName = cluster.name
                                            )
                                        }
                                )

                                HorizontalDivider()
                            }
                        }
                    }
                }
            }
        }
    }
}