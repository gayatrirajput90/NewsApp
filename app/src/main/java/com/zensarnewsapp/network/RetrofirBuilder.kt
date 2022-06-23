package com.zensarnewsapp.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofirBuilder {

    companion object {
        private val httpClient : OkHttpClient by lazy {
            OkHttpClient.Builder().build()
        }
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(Url.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
        }
        val api: Api by lazy {
            retrofit.create(Api::class.java)
        }
    }
}