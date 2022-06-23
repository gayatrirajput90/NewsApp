package com.zensarnewsapp.model


import com.google.gson.annotations.SerializedName

data class abc(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)