package com.abhi.lokaldemo.api.model

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("format") val format : String,
    @SerializedName("width") val width : Int,
    @SerializedName("height") val height : Int,
    @SerializedName("filename") val filename : String,
    @SerializedName("id") val id : Int,
    @SerializedName("author") val author : String,
    @SerializedName("author_url") val author_url : String,
    @SerializedName("post_url") val post_url : String
)