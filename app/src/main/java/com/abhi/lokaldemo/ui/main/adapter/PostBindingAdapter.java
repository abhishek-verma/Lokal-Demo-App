package com.abhi.lokaldemo.ui.main.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.abhi.lokaldemo.R;
import com.abhi.lokaldemo.api.model.PostResponse;
import com.abhi.lokaldemo.api.model.PostResponse.DownloadState;
import com.bumptech.glide.Glide;

public class PostBindingAdapter {
    private final static String LOG_TAG = PostBindingAdapter.class.getSimpleName();


    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView)
                .load(url + "/download")
                .error(R.drawable.cannot_load)
                .into(imageView);
    }


    @BindingAdapter("downloadButtonState")
    public static void showDownloadButton(View view, PostResponse.DownloadState downloadState) {
        if (downloadState.equals(DownloadState.DOWNLOAD_STATE_DEFAULT))
            view.setVisibility(View.VISIBLE);
        else
            view.setVisibility(View.GONE);
    }

    @BindingAdapter("downloadProgressState")
    public static void showDownloadProgress(View view, PostResponse.DownloadState downloadState) {
        if (downloadState.equals(DownloadState.DOWNLOAD_STATE_PROGRESS))
            view.setVisibility(View.VISIBLE);
        else
            view.setVisibility(View.GONE);
    }


}
