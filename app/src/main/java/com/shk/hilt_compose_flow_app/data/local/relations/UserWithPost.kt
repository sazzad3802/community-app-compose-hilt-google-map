package com.shk.hilt_compose_flow_app.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.shk.hilt_compose_flow_app.data.local.dto.PostDto
import com.shk.hilt_compose_flow_app.data.local.dto.UserDto


data class UserWithPosts(
    @Embedded val user: UserDto,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val posts: List<PostDto>
)
