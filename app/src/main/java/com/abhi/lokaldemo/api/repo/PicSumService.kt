package com.abhi.lokaldemo.api.repo

import com.abhi.lokaldemo.api.model.PostResponse
import retrofit2.Call
import retrofit2.http.GET


internal interface PicSumService {

    @GET("list")
    fun getPosts(): Call<List<PostResponse>>

    companion object {
        val BASE_URL = "https://picsum.photos/"
    }
}