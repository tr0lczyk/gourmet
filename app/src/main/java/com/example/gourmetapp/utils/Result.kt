package com.example.gourmetapp.utils

sealed class Result<out T : Any?> {
  data class Success<out T : Any?>(val data: T) : Result<T>()
  data class Failure(val failure: String) : Result<Nothing>()
  data class Error(val error: String) : Result<Nothing>()
}