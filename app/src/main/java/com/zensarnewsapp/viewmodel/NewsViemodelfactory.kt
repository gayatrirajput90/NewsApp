package com.zensarnewsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zensarnewsapp.repository.NewsRepository

class NewsViemodelfactory constructor(private val repository: NewsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewmodel(repository) as T
    }
}