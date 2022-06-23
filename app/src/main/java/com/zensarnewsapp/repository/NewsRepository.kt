package com.zensarnewsapp.repository

import com.zensarnewsapp.model.abc
import com.zensarnewsapp.network.RetrofirBuilder

class NewsRepository {

    suspend fun getAllNews(country: String,apiKey : String): abc = RetrofirBuilder.api.getAllNews(country,apiKey)
}