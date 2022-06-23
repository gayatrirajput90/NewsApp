package com.zensarnewsapp.model

import com.google.gson.annotations.SerializedName


/**
 * Created by Taiyab Ali on 14-Jan-20.
 */


data class Post(
        val id: String,
        val title: String,
        val body: String)
