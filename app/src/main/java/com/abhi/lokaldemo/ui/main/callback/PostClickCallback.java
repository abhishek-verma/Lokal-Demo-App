package com.abhi.lokaldemo.ui.main.callback;

import com.abhi.lokaldemo.api.model.PostResponse;
import com.abhi.lokaldemo.databinding.PostListItemBinding;

public interface PostClickCallback {

    public void onClick(PostResponse post);
}
