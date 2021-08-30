package com.example.gourmetapp.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.gourmetapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private val job = Job()
    private val newsListScope = CoroutineScope(Dispatchers.Main + job)
    private val newsFlow = repository.getAscendingNewsList()
    val news = newsFlow.asLiveData()
    val swipeRefreshing = MutableLiveData(true)

    init {
        refreshNewsList()
    }

    fun refreshNewsList() {
        newsListScope.launch {
            changeSwipeSetting(true)
            repository.downloadNewsList()
        }.invokeOnCompletion {
            changeSwipeSetting(false)
        }
    }

    private fun changeSwipeSetting(isRefreshing: Boolean) {
        swipeRefreshing.postValue(isRefreshing)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}