package com.shk.hilt_compose_flow_app.features.home.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shk.hilt_compose_flow_app.features.home.widgets.PostItem
import com.shk.hilt_compose_flow_app.features.home.viewsModels.HomeViewModel
import com.shk.hilt_compose_flow_app.utils.ConstData

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val postList = viewModel.postList
    val progressBarVisible by viewModel.progressBarVisibility.collectAsState()
    val paginationProgressVisible by viewModel.paginateProgressBarVisibility.collectAsState()
    var currentPage by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(postList.size) { index ->
                val post = postList[index]
                PostItem(post = post) {
                    viewModel.onClick(post)
                }
            }

            item {
                if (paginationProgressVisible) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }

                Button(
                    onClick = {
                        currentPage++
                        viewModel.fetchPosts(ConstData.limit, currentPage)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Load More")
                }
            }
        }

        if (progressBarVisible) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}