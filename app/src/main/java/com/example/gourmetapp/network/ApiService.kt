package com.example.gourmetapp.network

import com.example.gourmetapp.data.Meal
import retrofit2.Response
import retrofit2.http.GET

private const val MEAL_LIST = "recruitment-task"

interface ApiService {

  @GET(MEAL_LIST)
  suspend fun getMealList(): Response<List<Meal>>
}