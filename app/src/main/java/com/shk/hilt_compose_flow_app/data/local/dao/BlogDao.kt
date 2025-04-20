package com.shk.hilt_compose_flow_app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shk.hilt_compose_flow_app.data.local.dto.BlogDto

@Dao
interface BlogDao {
    @Query("SELECT * FROM blogs ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun getBlogs(limit: Int, offset: Int): List<BlogDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBlogs(blogs: List<BlogDto>)
}
