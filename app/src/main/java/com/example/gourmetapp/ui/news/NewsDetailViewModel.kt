package com.example.gourmetapp.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle) :
    ViewModel() {

    val newsDetail = MutableLiveData<String>()

        init {
            savedStateHandle.get<String>(NEWS_DETAIL_KEY)?.let {
                newsDetail.value = it
            }
        }

    companion object {
        const val NEWS_DETAIL_KEY = "newsDetail"
    }
}