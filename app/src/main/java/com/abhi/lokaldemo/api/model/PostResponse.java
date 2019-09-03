package com.abhi.lokaldemo.api.model;

public class PostResponse {
    public String format;
    public int width;
    public int height;
    public String filename;
    public int id;
    public String author;
    public String author_url;
    public String post_url;

    public DownloadState downloadState = DownloadState.DOWNLOAD_STATE_DEFAULT;
    public int downloadProgress = 0;

    public enum  DownloadState {
        DOWNLOAD_STATE_DEFAULT,
        DOWNLOAD_STATE_PROGRESS,
        DOWNLOAD_STATE_DOWNLOADED
    }
}
