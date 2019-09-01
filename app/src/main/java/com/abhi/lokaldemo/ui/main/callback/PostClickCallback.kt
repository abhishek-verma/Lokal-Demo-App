package com.abhi.lokaldemo.ui.main.callback

import com.abhi.lokaldemo.api.model.PostResponse

interface PostClickCallback {
    fun onClick(post: PostResponse)
}