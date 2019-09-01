package com.abhi.lokaldemo.viewmodel

import androidx.lifecycle.LiveData
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.abhi.lokaldemo.api.model.PostResponse
import com.abhi.lokaldemo.api.repo.PostResponseRepository


class PostListViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * Expose the LiveData PostResponse query so the UI can observe it.
     */
    val projectListObservable: LiveData<List<PostResponse>> = PostResponseRepository.getInstance().getPostResponseList()
}
