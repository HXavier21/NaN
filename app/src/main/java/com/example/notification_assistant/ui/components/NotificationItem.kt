package com.example.notification_assistant.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notification_assistant.R
import com.example.notification_assistant.ui.theme.Notification_AssistantTheme


@Composable
fun NotificationItem(
    modifier: Modifier = Modifier,
    appIcon: Painter? = null,
    appName: String? = null,
    time: String? = null,
    title: String,
    details: String,
    num: Int? = null,
    expanded: Boolean = false,
    onExpandClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier.clickable(interactionSource = interactionSource, indication = null) {
            onExpandClick()
        }
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            appIcon?.let {
                Icon(
                    painter = appIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(top = 3.dp)
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                )
            }
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Row(
                    modifier = Modifier.height(32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    appName?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelLarge
                        )
                        time?.let {
                            Text(
                                text = " · ",
                                fontWeight = FontWeight.W900,
                                color = Color.Gray
                            )
                        }
                    }
                    time?.let {
                        Text(
                            text = it,
                            color = Color.Gray,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .widthIn(min = 48.dp)
                            .height(28.dp)
                            .clip(MaterialTheme.shapes.large)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = LocalIndication.current
                            ) { onExpandClick() },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        num?.let {
                            Text(
                                text = "$it",
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .padding(vertical = 4.dp),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                        val animatedRotation by animateFloatAsState(
                            targetValue = if (expanded) 180f else 0f,
                            label = ""
                        )
                        Icon(
                            imageVector = Icons.Default.ExpandMore,
                            contentDescription = "Expand",
                            modifier = Modifier
                                .scale(0.8f)
                                .graphicsLayer {
                                    rotationZ = animatedRotation
                                }
                        )
                    }
                }
                AnimatedContent(
                    targetState = expanded,
                    modifier = Modifier
                        .padding(top = 16.dp, end = 4.dp), label = ""
                ) { targetState ->
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = details,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = if (targetState) Int.MAX_VALUE else 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun NotificationItemPreview() {
    Notification_AssistantTheme {
        var expanded by remember { mutableStateOf(false) }
        NotificationItem(
            modifier = Modifier.padding(16.dp),
            appIcon = painterResource(R.drawable.ic_launcher_foreground),
            appName = "QQ",
            time = "4 days ago",
            title = "通知群：软件学院2022级本科生",
            details = "[@全体成员]田春雨：@全体成员 【关于2024年秋季学期家庭经济困难学生认定与复核工作的通知】\n" +
                    "1.首次参与认定或需要调整认定等级的学生，需在系统里填写申请；无需调整的不必申请，将按之前等级顺延，但请登陆系统查看当前的困难认定等级是否正确，若正确则无需其他操作；学生工作综合管理系统网址：http://xgxt.hust.edu.cn/xg；\n" +
                    "2.学生申请时间：2024年10月16日（周三）24:00前；\n" +
                    "\n" +
                    "详细内容和附件见文档\n" +
                    "【腾讯文档】2024年秋季家庭困难认定通知\n" +
                    "https://docs.qq.com/doc/DWHJZUXlTdlJJeGtz",
            expanded = expanded,
            onExpandClick = { expanded = !expanded },
        )
    }
}