package com.example.gourmetapp.ui.meal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.gourmetapp.repository.GourmetAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealListViewModel @Inject constructor(repository: GourmetAppRepository) : ViewModel() {

  private val job = Job()
  private val mealListScope = CoroutineScope(Dispatchers.Main + job)
  private val mealFlow = repository.getMealList()
  val meals = mealFlow.asLiveData()

  init {
    mealListScope.launch {
      repository.downloadMealList()
    }
  }

  override fun onCleared() {
    super.onCleared()
    job.cancel()
  }
}