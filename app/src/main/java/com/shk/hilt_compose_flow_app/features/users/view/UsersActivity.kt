package com.shk.hilt_compose_flow_app.features.users.view


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shk.hilt_compose_flow_app.data.local.dto.PostDto
import com.shk.hilt_compose_flow_app.services.UserDataFetchService
import com.shk.hilt_compose_flow_app.utils.ConstData
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.compose.setContent
import com.shk.hilt_compose_flow_app.R
import com.shk.hilt_compose_flow_app.features.users.viewmodel.UserViewModel
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import com.shk.hilt_compose_flow_app.ui.theme.HiltcomposeflowappTheme

@AndroidEntryPoint
class UsersActivity : ComponentActivity() {
    private val viewModel: UserViewModel by viewModels()
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startService(Intent(this, UserDataFetchService::class.java))

        setContent {
            HiltcomposeflowappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PostScreen(
                        viewModel = viewModel,
                        onPostClick = { post ->

                        },
                        onLoadMore = {
                            currentPage++
                            viewModel.fetchPosts(ConstData.limit, currentPage)
                        }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, UserDataFetchService::class.java))
    }
}

@Composable
fun PostScreen(
    viewModel: UserViewModel,
    onPostClick: (PostDto) -> Unit,
    onLoadMore: () -> Unit
) {
    val posts = viewModel.postList

    val isLoading by viewModel.isLoading.collectAsState()
    val showProgress by viewModel.progressBarVisibility.collectAsState()
    val showPaginateProgress by viewModel.paginateProgressBarVisibility.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.selectedPost.collect { post ->
            onPostClick(post)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(posts.size) { index ->
                val post = posts[index]
                PostItem(
                    post = post,
                    onClick = { viewModel.onClick(post) }
                )
            }

            if (showPaginateProgress) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }

            if (!isLoading && posts.isNotEmpty()) {
                item {
                    LaunchedEffect(Unit) {
                        onLoadMore()
                    }
                }
            }
        }

        if (showProgress) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}


@Composable
fun PostItem(post: PostDto, onClick: () -> Unit) {
    val imageResources = listOf(R.drawable.jc1, R.drawable.jc2, R.drawable.jc3)
    val imageRes = imageResources[post.id.toInt() % imageResources.size]

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Post image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = post.title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = post.body,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconWithText(
                    icon = Icons.Default.ThumbUp,
                    text = post.likes.toString()
                )

                IconWithText(
                    icon = Icons.Default.ThumbDown,
                    text = post.dislikes.toString()
                )

                IconWithText(
                    icon = Icons.Default.Comment,
                    text = post.views.toString()
                )
            }
        }
    }
}

@Composable
fun IconWithText(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, style = MaterialTheme.typography.bodySmall)
    }
}