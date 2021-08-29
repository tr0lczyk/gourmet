package com.example.gourmetapp.repository

import android.content.Context
import android.widget.Toast
import com.example.gourmetapp.data.News
import com.example.gourmetapp.data.NewsDatabase
import com.example.gourmetapp.utils.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class NewsRepository @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val networkRepository: NetworkRepository,
    private val database: NewsDatabase
) {

    suspend fun downloadNewsList() {
        when (val response = networkRepository.getNewsList()) {
            is Result.Success -> {
                database.newsDao().insertList(response.data!!)
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

    fun getNewsList(): Flow<List<News>> {
        return database.newsDao().getAscendingNews()
    }
}