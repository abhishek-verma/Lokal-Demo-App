package com.abhi.lokaldemo.api.repo;

import com.abhi.lokaldemo.api.model.PostResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PicSumService {

    String BASE_URL = "https://picsum.photos/";

    @GET("list")
    Call<List<PostResponse>> getPosts();

}
