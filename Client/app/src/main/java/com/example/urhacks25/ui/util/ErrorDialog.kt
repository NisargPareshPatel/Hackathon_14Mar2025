package com.example.urhacks25.ui.util

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ErrorDialog(error: String, dismissError: () -> Unit) {
    if (error.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = dismissError,
            title = {
                Text("Error")
            }, text = {
                Text(error)
            }, confirmButton = {
                TextButton(dismissError) {
                    Text("OK")
                }
            }
        )
    }
}