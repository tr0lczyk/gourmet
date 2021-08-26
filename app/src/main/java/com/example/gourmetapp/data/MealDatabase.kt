package com.example.gourmetapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Meal::class], version = 1)
abstract class MealDatabase : RoomDatabase() {

  abstract fun mealDao(): MealDao
}