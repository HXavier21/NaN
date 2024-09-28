package com.example.notification_assistant.ui.components

import android.view.MotionEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notification_assistant.ui.theme.Notification_AssistantTheme

@Composable
fun MainBottomBar() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    MainBottomBarImpl(
        selectedIndex = selectedIndex,
        onItemClick = { selectedIndex = it }
    )
}

@Composable
fun MainBottomBarImpl(
    selectedIndex: Int = 0,
    onItemClick: (Int) -> Unit = {}
) {
    BottomAppBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
        AnimatedIcon(
            modifier = Modifier.weight(1f),
            selected = selectedIndex == 0,
            previousIcon = Icons.Default.Notifications,
            nextIcon = Icons.Default.NotificationsActive,
            tint = MaterialTheme.colorScheme.primary,
            panel = "Notifications",
            onIconClick = { onItemClick(0) }
        )
        AnimatedIcon(
            modifier = Modifier.weight(1f),
            selected = selectedIndex == 1,
            previousIcon = Icons.AutoMirrored.Filled.InsertDriveFile,
            nextIcon = Icons.Default.Summarize,
            tint = MaterialTheme.colorScheme.primary,
            panel = "Summary",
            onIconClick = { onItemClick(1) }
        )
        AnimatedIcon(
            modifier = Modifier.weight(1f),
            selected = selectedIndex == 2,
            previousIcon = Icons.Default.Settings,
            tint = MaterialTheme.colorScheme.primary,
            panel = "Files",
            onIconClick = { onItemClick(2) },
            enableRotate = true
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AnimatedIcon(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    previousIcon: ImageVector,
    nextIcon: ImageVector? = null,
    tint: Color = MaterialTheme.colorScheme.primary,
    panel: String? = null,
    onIconClick: () -> Unit = {},
    enableRotate: Boolean = false,
    tween: Int = 500
) {
    var isPressed by remember { mutableStateOf(false) }
    val animatedScale by animateFloatAsState(
        targetValue = when (selected) {
            true -> if (isPressed) 1.0f else 1.2f
            false -> if (isPressed) 0.7f else 0.9f
        }, label = "",
        animationSpec = tween(tween, easing = LinearOutSlowInEasing)
    )
    val animatedRotation by animateFloatAsState(
        targetValue = if (selected) 36f else 0f,
        label = "",
        animationSpec = tween(tween, easing = LinearOutSlowInEasing)
    )
    Column(
        modifier = Modifier
            .scale(animatedScale)
            .pointerInteropFilter {
                if (it.action == MotionEvent.ACTION_DOWN) {
                    isPressed = true
                } else if (it.action == MotionEvent.ACTION_UP) {
                    isPressed = false
                    onIconClick()
                }
                true
            }
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = if (selected) nextIcon ?: previousIcon else previousIcon,
            contentDescription = "",
            tint = tint,
            modifier = Modifier
                .graphicsLayer {
                    if (enableRotate) {
                        rotationZ = animatedRotation
                    }
                }
        )
        panel?.let {
            AnimatedVisibility(visible = !selected) {
                Text(
                    text = panel,
                    color = tint,
                    modifier = Modifier,
                    style = MaterialTheme.typography.labelSmall
                )
            }
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

@Preview
@Composable
fun AnimatedIconPreview() {
    Notification_AssistantTheme {
        var selected by remember { mutableStateOf(false) }
        AnimatedIcon(
            modifier = Modifier.height(68.dp),
            selected = selected,
            Icons.Default.Notifications,
            Icons.Default.NotificationsActive,
            onIconClick = { selected = !selected },
            panel = "Notifications"
        )
    }
}