package com.example.cat

import com.example.cat.api.SpaceNewsJson
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequests {

    @GET ("/articles/1")
    fun getSpaceNews(): Call<SpaceNewsJson>
}