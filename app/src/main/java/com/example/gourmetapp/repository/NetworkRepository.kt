package com.example.gourmetapp.repository

import com.example.gourmetapp.data.Meal
import com.example.gourmetapp.network.ApiService
import com.example.gourmetapp.utils.BaseNetworkRepository
import com.example.gourmetapp.utils.Result
import javax.inject.Inject

class NetworkRepository @Inject constructor(
  private val apiService: ApiService
) : BaseNetworkRepository() {

  suspend fun getMealList(): Result<List<Meal>?> {
    return baseApiCall(
      block = {
        apiService.getMealList()
      }
    )
  }
}