package com.zensarnewsapp.network

import com.zensarnewsapp.model.abc
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("top-headlines")
    suspend fun getAllNews(@Query("country") country: String,@Query("apiKey") apiKey: String) : abc
}