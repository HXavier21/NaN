package com.example.notification_assistant.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notification_assistant.ui.theme.Notification_AssistantTheme

@Composable
fun SettingSection(
    modifier: Modifier = Modifier,
    sectionName: String = "",
    settingList: List<@Composable () -> Unit> = listOf()
) {
    Column(modifier = modifier) {
        Text(
            text = sectionName, color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
        Card(
            modifier = Modifier
                .wrapContentSize()
                .clip(MaterialTheme.shapes.medium)
                .shadow(20.dp, shape = MaterialTheme.shapes.medium),
            colors = CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
            ),
            elevation = CardDefaults.elevatedCardElevation()

        ) {
            for (settingItem in settingList.dropLast(1)) {
                settingItem()
                HorizontalDivider(modifier = Modifier)
            }
            settingList.last()()
        }
    }
}

@Composable
fun SettingItem(
    leadingIcon: (@Composable () -> Unit)? = null,
    settingText: String,
    defaultValue: String? = null,
    isSwitch: Boolean = false,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    onItemClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onItemClick() }
            .padding(vertical = 16.dp, horizontal = 8.dp)

    ) {
        leadingIcon?.let {
            it()
        }
        var textHeight by remember { mutableIntStateOf(0) }
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
                .onGloballyPositioned {
                    textHeight = it.size.height
                },
            text = settingText
        )
        when (isSwitch) {
            true -> Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.height(textHeight.pxToDp())
            )

            false -> {
                defaultValue?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

@Composable
@Preview
fun SettingSectionPreview() {
    Notification_AssistantTheme {
        Surface(color = MaterialTheme.colorScheme.primaryContainer) {
            SettingSection(
                modifier = Modifier.padding(16.dp),
                sectionName = "Reject Screen",
                settingList = listOf(
                    {
                        SettingItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            settingText = "Screen Usage",
                            defaultValue = "On"
                        )
                    },
                    {
                        SettingItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            settingText = "Dark Mode",
                            isSwitch = true,
                            checked = true
                        )
                    },
                    {
                        SettingItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            settingText = "Online Mode",
                            isSwitch = true
                        )
                    },
                    {
                        SettingItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            settingText = "Screen Usage"
                        )
                    }
                )
            )
        }
    }
}