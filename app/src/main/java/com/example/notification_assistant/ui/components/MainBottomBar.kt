package com.example.notification_assistant.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.notification_assistant.ui.theme.Notification_AssistantTheme

@Composable
fun MainBottomBar() {
    BottomAppBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
        Row(modifier = Modifier.fillMaxWidth()) {

        }
    }
}

@Preview
@Composable
fun MainBottomBarPreview() {
    Notification_AssistantTheme {
        MainBottomBar()
    }
}