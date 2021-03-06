package com.zensarnewsapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zensarnewsapp.model.News
import com.zensarnewsapp.repository.NewsRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class NewsViewmodel constructor(private val repository: NewsRepository): ViewModel() {

    val newsLiveData: LiveData<News> get() = newsList
    val newsList: MutableLiveData<News> = MutableLiveData()

    //This function get the response from new api using coroutines
    fun getAllNews(country: String,apiKey : String) {

        // It launches the coroutine
        viewModelScope.launch {
            try {
                val response = repository.getAllNews(country,apiKey)
                newsList.value = response
                Log.d("main", "Success")
            } catch (e: Exception){
                Log.d("main", "getmsg- ${e.message}")
            }
        }
    }


}


