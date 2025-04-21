package com.shk.hilt_compose_flow_app.features.notification.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shk.hilt_compose_flow_app.R

@Composable
fun NotificationScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn {
            items(notificationList.size) { index ->
                val item = notificationList[index]
                NotificationItemView(item)
                Divider()
            }
        }
    }
}


@Composable
fun NotificationItemView(item: NotificationItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = item.profileImage),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = item.message,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = item.time,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }

        item.reactionIcon?.let {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = it),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Unspecified
            )
        }
    }
}



data class NotificationItem(
    val profileImage: Int,
    val name: String,
    val message: String,
    val time: String,
    val reactionIcon: Int? = null
)

// Sample data
val notificationList = listOf(
    NotificationItem(R.drawable.ic_profile_empty, "Lionel Messi", "poked you.", "2d", R.drawable.ic_poke),
    NotificationItem(R.drawable.ic_profile_empty, "Cristiano Ronaldo", "mentioned you in a comment.", "2d", R.drawable.ic_comment),
    NotificationItem(R.drawable.ic_profile_empty, "Gareth Bale", "reacted to your comment: \"Best wishes bhai ❤️\".", "2d", R.drawable.ic_heart),
    NotificationItem(R.drawable.ic_profile_empty, "Mohammed Salah", "and 3 other people recently reacted to your photo.", "3d", R.drawable.ic_heart),
    NotificationItem(R.drawable.ic_profile_empty, "Neymar da Silva", "commented on your photo.", "3d", R.drawable.ic_comment),
    NotificationItem(R.drawable.ic_profile_empty, "Ronaldinho Gaucho", "highlighted a comment for you to check out", "6d", R.drawable.ic_comment),
)