package com.abhi.lokaldemo.api.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.abhi.lokaldemo.api.model.PostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


class PostResponseRepository private constructor() {
    private val picSumService: PicSumService

    init {
        //TODO this gitHubService instance will be injected using Dagger in part #2 ...
        val retrofit = Retrofit.Builder()
            .baseUrl(PicSumService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        picSumService = retrofit.create(PicSumService::class.java)
    }

    fun getPostResponseList(userId: String): LiveData<List<PostResponse>> {
        val data = MutableLiveData<List<PostResponse>>()

        picSumService.getPosts().enqueue(object : Callback<List<PostResponse>> {
            override fun onResponse(call: Call<List<PostResponse>>, response: Response<List<PostResponse>>) {
                data.setValue(response.body())
            }

            override fun onFailure(call: Call<List<PostResponse>>, t: Throwable) {
                // TODO better error handling in part #2 ...
                data.setValue(null)
            }
        })

        return data
    }

    companion object {
        @Volatile private var INSTANCE: PostResponseRepository ? = null
        fun  getInstance(): PostResponseRepository {
            return INSTANCE?: synchronized(this){
                PostResponseRepository().also {
                    INSTANCE = it
                }
            }
        }
    }
}