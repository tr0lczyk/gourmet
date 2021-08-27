package com.example.gourmetapp.ui.meal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle) :
    ViewModel() {

    val mealDetail = MutableLiveData<String>()

        init {
            savedStateHandle.get<String>(MEAL_DETAIL_KEY)?.let {
                mealDetail.value = it
            }
        }

    companion object {
        const val MEAL_DETAIL_KEY = "mealDetail"
    }
}