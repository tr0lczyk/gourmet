package com.example.gourmetapp.utils

import retrofit2.Response

open class BaseNetworkRepository {

  suspend fun <T : Any> baseApiCall(block: suspend () -> Response<T>): Result<T?> {
    return try {
      val response = block.invoke()
      if (response.isSuccessful) {
        Result.Success(response.body())
      } else {
        Result.Failure(response.code().toString())
      }
    } catch (e: Exception) {
      Result.Error(e.message ?: "")
    }
  }
}