package com.shk.hilt_compose_flow_app.features.home.viewsModels

import androidx.lifecycle.ViewModel
import com.shk.hilt_compose_flow_app.base.ViewState
import com.shk.hilt_compose_flow_app.data.local.dto.PostDto
import com.shk.hilt_compose_flow_app.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import com.shk.hilt_compose_flow_app.data.listeners.ItemClickListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import com.shk.hilt_compose_flow_app.utils.ConstData

@HiltViewModel
class HomeViewModel @Inject constructor(
    private var postRepository: PostRepository
) : ViewModel(), ItemClickListener {

    val postList = mutableStateListOf<PostDto>()

    val progressBarVisibility = MutableStateFlow(false)
    val paginateProgressBarVisibility = MutableStateFlow(false)
    val isLoading = MutableStateFlow(false)
    val selectedPost = MutableSharedFlow<PostDto>()

    init {
        fetchPosts(ConstData.limit, 0)
    }

    fun fetchPosts(limit: Int, page: Int) {
        viewModelScope.launch {
            try {
                postRepository.fetchPost(limit, page).collect {
                    when (it) {
                        is ViewState.Loading -> {
                            progressBarVisibility.emit(true)
                            paginateProgressBarVisibility.emit(false)
                            isLoading.emit(true)
                        }
                        is ViewState.PaginationLoading -> {
                            progressBarVisibility.emit(false)
                            paginateProgressBarVisibility.emit(true)
                            isLoading.emit(true)
                        }
                        is ViewState.Success -> {
                            it.data?.let { newPosts ->
                                progressBarVisibility.emit(false)
                                paginateProgressBarVisibility.emit(false)
                                isLoading.emit(false)
                                if (page == 0) {
                                    postList.clear()
                                }
                                postList.addAll(newPosts)
                            }
                        }
                        is ViewState.Error -> {
                            progressBarVisibility.value = false
                            paginateProgressBarVisibility.value = false
                            isLoading.value = false
                        }
                    }
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    override fun onClick(data: Any) {
        viewModelScope.launch {
            selectedPost.emit(data as PostDto)
        }
    }
}