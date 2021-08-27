package com.example.gourmetapp.ui.meal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.gourmetapp.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealListViewModel @Inject constructor(private val repository: MealRepository) : ViewModel() {

    private val job = Job()
    private val mealListScope = CoroutineScope(Dispatchers.Main + job)
    private val mealFlow = repository.getMealList()
    val meals = mealFlow.asLiveData()
    val swipeRefreshing = MutableLiveData(true)

    init {
        refreshMealList()
    }

    fun refreshMealList() {
        mealListScope.launch {
            changeSwipeSetting(true)
            repository.downloadMealList()
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