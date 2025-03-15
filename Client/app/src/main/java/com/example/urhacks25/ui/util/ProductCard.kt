package com.example.urhacks25.ui.util

import android.text.format.DateFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.urhacks25.core.api_model.ApiProductModel
import com.example.urhacks25.core.api_model.ApiUserModel
import java.util.TimeZone

@Composable
fun ProductCard(
    item: ApiProductModel,
    canGift: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        ListItem(
            headlineContent = {
                Text(item.name)
            }, supportingContent = {
                Text("${item.price} CA$")
            }, overlineContent = {
                val ctx = LocalContext.current

                val pDate = remember(item.expiry) {
                    DateFormat.getDateFormat(ctx)
                        .also { it.timeZone = TimeZone.getTimeZone("UTC") }
                        .format(item.expiry.toEpochMilliseconds()).toString()
                }

                Text("Expires at $pDate")
            }, leadingContent = {
                AsyncImage(
                    model = item.photoUrl,
                    modifier = Modifier.size(64.dp).clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)),
                    error = ColorPainter(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)),
                )
            }, trailingContent = if (canGift) {
                {
                    IconButton(onClick = {

                    }) {
                        Icon(Icons.Default.CardGiftcard, contentDescription = null)
                    }
                }
            } else null, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), colors = ListItemDefaults.colors(containerColor = Color.Transparent)
        )
    }
}

@Composable
fun BookedProductCard(
    item: ApiProductModel,
    user: ApiUserModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(Modifier.padding(vertical = 4.dp)) {
            ListItem(
                headlineContent = {
                    Text(item.name)
                }, supportingContent = {
                    Text("${item.price} CA$")
                }, overlineContent = {
                    val ctx = LocalContext.current

                    val pDate = remember(item.expiry) {
                        DateFormat.getDateFormat(ctx)
                            .also { it.timeZone = TimeZone.getTimeZone("UTC") }
                            .format(item.expiry.toEpochMilliseconds()).toString()
                    }

                    Text("Expires at $pDate")
                }, leadingContent = {
                    AsyncImage(
                        model = item.photoUrl,
                        modifier = Modifier.size(64.dp).clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)),
                        error = ColorPainter(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)),
                    )
                }, modifier = Modifier.fillMaxWidth(), colors = ListItemDefaults.colors(containerColor = Color.Transparent)
            )

            HorizontalDivider()

            ListItem(
                headlineContent = {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(user.firstName)
                        Text(user.lastName)
                    }
                }, supportingContent = {
                    Text(user.phone.toString())
                }, trailingContent = {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = null)
                    }
                }, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), colors = ListItemDefaults.colors(containerColor = Color.Transparent)
            )
        }
    }
}