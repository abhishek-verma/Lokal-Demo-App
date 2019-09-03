package com.abhi.imagedownloader;

public interface ImageDownloaderListener {

    void onError(ImageDownloadError error);
    void onProgressChange(int percent);
    void onComplete(String filePath);
}
