package com.example.android_ap.ui.popups

import android.app.Activity
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun Warning(
    texto: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {

    AlertDialog(
        onDismissRequest = {
            onClose()
        },
        title = { Text(text = "Aviso") },
        text = { Text(text = texto) },
        modifier = modifier,
        confirmButton = {
            TextButton(onClick = { onClose() }) {
                Text(text = "OK")
            }
        }
    )
}