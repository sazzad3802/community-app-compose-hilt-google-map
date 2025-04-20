package com.shk.hilt_compose_flow_app.features.dashboard.views

import dagger.hilt.android.HiltAndroidApp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.shk.hilt_compose_flow_app.ui.theme.HiltcomposeflowappTheme
import com.shk.hilt_compose_flow_app.R
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.shk.hilt_compose_flow_app.utils.noRippleClickable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HiltcomposeflowappTheme {
                // A surface container using the 'background' color from the theme
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

    Scaffold(
        topBar = {
            Column {
                TopAppBar(title = { Text("Community") })
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
                    TabItem.Marketplace -> MarketplaceScreen()
                    TabItem.Notifications -> NotificationsScreen()
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

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Home Feed",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun FriendRequestScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Friends Requests",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TravelDestScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Travel Plans",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MarketplaceScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Marketplace",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun NotificationsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Notifications",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
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