package com.example.gourmetapp.network

import com.example.gourmetapp.data.News
import retrofit2.Response
import retrofit2.http.GET

private const val NEWS_LIST = "recruitment-task"

interface ApiService {

  @GET(NEWS_LIST)
  suspend fun getNewsList(): Response<List<News>>
}