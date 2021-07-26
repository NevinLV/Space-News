package com.example.cat

import com.example.cat.api.News
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequests {

    @GET ("articles?_start=0&_limit=3")
    fun getSpaceNews(): Call<News>
}