package com.abhi.lokaldemo.ui.main.adapter;

import android.view.View;

import androidx.databinding.BindingAdapter;

public class LoadingBindingAdapter {

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
