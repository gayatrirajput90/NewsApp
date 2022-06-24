package com.zensarnewsapp.network

import com.zensarnewsapp.model.News
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("top-headlines")
    suspend fun getAllNews(@Query("country") country: String,@Query("apiKey") apiKey: String) : News
}