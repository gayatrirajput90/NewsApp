package com.zensarnewsapp.repository

import com.zensarnewsapp.model.News
import com.zensarnewsapp.network.RetrofirBuilder

class NewsRepository {

    suspend fun getAllNews(country: String,apiKey : String): News = RetrofirBuilder.api.getAllNews(country,apiKey)
}