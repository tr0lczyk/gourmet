package com.example.gourmetapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface MealDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(meal: Meal)

  @Delete
  suspend fun delete(meal: Meal)

  @Update
  suspend fun update(meal: Meal)
}