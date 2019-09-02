package com.abhi.lokaldemo.api.repo;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abhi.lokaldemo.api.model.PostResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class PostResponseRepository {
    private PicSumService picSumService;


    @Inject
    public PostResponseRepository(PicSumService picSumService) {
        this.picSumService = picSumService;
    }

    public LiveData<List<PostResponse>> getPostResponseList() {

        final MutableLiveData<List<PostResponse>> data = new MutableLiveData<>();

        picSumService.getPosts().enqueue(new Callback<List<PostResponse>>() {

            @Override
            public void onResponse(Call<List<PostResponse>> call, Response<List<PostResponse>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<PostResponse>> call, Throwable t) {
                // TODO better error handling
                data.setValue(null);
            }
        });

        return data;
    }

}
