package com.abhi.imagedownloader;


import androidx.annotation.NonNull;

public class ImageDownloadError extends Exception {

    public ImageDownloadError(@NonNull String message) {
        super(message);
    }

    public ImageDownloadError(@NonNull Throwable error) {
        super(error.getMessage(), error.getCause());
        this.setStackTrace(error.getStackTrace());
    }

}