package com.example.gourmetapp.repository

import com.example.gourmetapp.data.News
import com.example.gourmetapp.network.ApiService
import com.example.gourmetapp.utils.BaseNetworkRepository
import com.example.gourmetapp.utils.Result
import javax.inject.Inject

class NetworkRepository @Inject constructor(
  private val apiService: ApiService
) : BaseNetworkRepository() {

  suspend fun getNewsList(): Result<List<News>?> {
    return baseApiCall(
      block = {
        apiService.getNewsList()
      }
    )
  }
}