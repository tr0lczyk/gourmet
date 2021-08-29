package com.example.gourmetapp.repository

import android.content.Context
import android.widget.Toast
import com.example.gourmetapp.data.Meal
import com.example.gourmetapp.data.MealDatabase
import com.example.gourmetapp.utils.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class MealRepository @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val networkRepository: NetworkRepository,
    private val database: MealDatabase
) {

    suspend fun downloadMealList() {
        when (val response = networkRepository.getMealList()) {
            is Result.Success -> {
                database.mealDao().insertList(response.data!!)
            }
            is Result.Failure -> {
                Toast.makeText(appContext, response.failure, Toast.LENGTH_LONG).show()
                Timber.i("responseSchema: ${response.failure}")
            }
            is Result.Error -> {
                Toast.makeText(appContext, response.error, Toast.LENGTH_LONG).show()
                Timber.i("responseSchema: ${response.error}")
            }
        }
    }

    fun getMealList(): Flow<List<Meal>> {
        return database.mealDao().getAscendingMeals()
    }
}