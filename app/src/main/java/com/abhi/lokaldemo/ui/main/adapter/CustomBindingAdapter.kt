package com.abhi.lokaldemo.ui.main.adapter

import android.view.View

import androidx.databinding.BindingAdapter

object CustomBindingAdapter {
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
}