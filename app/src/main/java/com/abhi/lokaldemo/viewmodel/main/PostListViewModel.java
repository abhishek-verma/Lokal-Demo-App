package com.abhi.lokaldemo.viewmodel.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.abhi.lokaldemo.LokalApplication;
import com.abhi.lokaldemo.api.model.PostResponse;
import com.abhi.lokaldemo.api.repo.PostResponseRepository;

import java.util.List;

import javax.inject.Inject;

public class PostListViewModel extends AndroidViewModel {

    private final LiveData<List<PostResponse>> postListObservable;

    @Inject
    public PostListViewModel(@NonNull PostResponseRepository postResponseRepository, @NonNull LokalApplication application) {
        super(application);
        postListObservable = postResponseRepository.getPostResponseList();
    }

    public LiveData<List<PostResponse>> getPostListObservable() {
        return postListObservable;
    }
}
