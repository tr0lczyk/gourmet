package com.example.gourmetapp.repository

import com.example.gourmetapp.data.Meal
import com.example.gourmetapp.data.MealDatabase
import com.example.gourmetapp.utils.Result
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class GourmetAppRepository @Inject constructor(
  private val networkRepository: NetworkRepository,
  private val database: MealDatabase
) {

  suspend fun downloadMealList() {
    when (val response = networkRepository.getMealList()) {
      is Result.Success -> {
        database.mealDao().insertList(response.data!!)
      }
      is Result.Failure -> Timber.i("responseSchema: ${response.failure}")
      is Result.Error -> Timber.i("responseSchema: ${response.error}")
    }
  }

  fun getMealList(): Flow<List<Meal>>{
    return database.mealDao().getAscendingMeals()
  }
}