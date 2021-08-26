package com.example.gourmetapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(meal: Meal)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertList(meals: List<Meal>)

  @Delete
  suspend fun delete(meal: Meal)

  @Update
  suspend fun update(meal: Meal)

  @Query("SELECT * FROM meal_table ORDER BY orderId ASC")
  fun getAscendingMeals(): Flow<List<Meal>>
}