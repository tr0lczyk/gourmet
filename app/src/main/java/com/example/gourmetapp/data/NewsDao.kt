package com.example.gourmetapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(news: News)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertList(news: List<News>)

  @Delete
  suspend fun delete(news: News)

  @Update
  suspend fun update(news: News)

  @Query("SELECT * FROM news_table ORDER BY orderId ASC")
  fun getAscendingNews(): Flow<List<News>>

  @Query("DELETE FROM news_table")
  fun nukeNews()
}