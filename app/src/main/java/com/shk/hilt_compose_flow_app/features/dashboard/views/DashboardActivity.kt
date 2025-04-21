package com.shk.hilt_compose_flow_app.features.dashboard.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.shk.hilt_compose_flow_app.ui.theme.HiltcomposeflowappTheme
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.shk.hilt_compose_flow_app.features.friendRequest.views.FriendRequestScreen
import com.shk.hilt_compose_flow_app.features.home.views.HomeScreen
import com.shk.hilt_compose_flow_app.features.marketPlace.views.MarketPlaceScreen
import com.shk.hilt_compose_flow_app.features.notification.views.NotificationScreen
import com.shk.hilt_compose_flow_app.features.travelList.views.TravelDestScreen
import com.shk.hilt_compose_flow_app.utils.noRippleClickable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HiltcomposeflowappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CommunityContent()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityContent() {
    var currentTab by remember { mutableStateOf(TabItem.Home) }

    val appBarTitle = when (currentTab) {
        TabItem.Home -> "Community"
        TabItem.Friends -> "Friend requests"
        TabItem.Travel -> "Travel Plans"
        TabItem.Marketplace -> "Marketplace"
        TabItem.Notifications -> "Notifications"
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(title = { Text(appBarTitle) },
                    actions = {
                        // Search icon button
                        IconButton(
                            onClick = { /* Handle search action */ }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                    },)
                Surface(color = Color.LightGray) {
                    TabRow(
                        selectedTabIndex = currentTab.ordinal,
                        modifier = Modifier.height(40.dp),
                        divider = {},
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                Modifier
                                    .tabIndicatorOffset(tabPositions[currentTab.ordinal])
                                    .height(2.dp)
                                    .padding(horizontal = 24.dp)
                            )
                        }
                    ) {
                        TabItem.entries.forEach { tab ->
                            Tab(
                                selected = currentTab == tab,
                                onClick = { currentTab = tab },
                                modifier = Modifier.height(40.dp)
                                    .clip(MaterialTheme.shapes.small)
                                    .noRippleClickable { currentTab = tab },
                                content = {
                                    CenteredTabContent(
                                        icon = tab.icon,
                                        text = tab.title,
                                        isSelected = currentTab == tab
                                    )
                                }
                            )
                        }
                    }
                }
            }
        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                when (currentTab) {
                    TabItem.Home -> HomeScreen()
                    TabItem.Friends -> FriendRequestScreen()
                    TabItem.Travel -> TravelDestScreen()
                    TabItem.Marketplace -> MarketPlaceScreen()
                    TabItem.Notifications -> NotificationScreen()
                }
            }
        }
    )
}

@Composable
private fun CenteredTabContent(
    icon: ImageVector,
    text: String,
    isSelected: Boolean
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(24.dp),
                tint = if (isSelected) Color.Black else Color.Gray
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = text,
                fontSize = 12.sp,
                color = if (isSelected) Color.Black else Color.Gray
            )
        }
    }
}



enum class TabItem(
    val title: String,
    val icon: ImageVector
) {
    Home("", Icons.Filled.Home),
    Friends("", Icons.Filled.People),
    Travel("", Icons.Filled.Flight),
    Marketplace("", Icons.Filled.Store),
    Notifications("", Icons.Filled.Notifications)
}