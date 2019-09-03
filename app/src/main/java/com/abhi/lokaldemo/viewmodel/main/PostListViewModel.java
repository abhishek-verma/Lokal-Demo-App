package com.abhi.lokaldemo.viewmodel.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.PermissionChecker;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.abhi.imagedownloader.ImageDownloadError;
import com.abhi.imagedownloader.ImageDownloader;
import com.abhi.imagedownloader.ImageDownloaderListener;
import com.abhi.lokaldemo.LokalApplication;
import com.abhi.lokaldemo.R;
import com.abhi.lokaldemo.api.model.PostResponse;
import com.abhi.lokaldemo.api.repo.PostResponseRepository;
import com.abhi.lokaldemo.databinding.PostListItemBinding;
import com.abhi.lokaldemo.ui.main.callback.PostClickWithBindingCallback;

import java.util.List;

import javax.inject.Inject;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

public class PostListViewModel extends AndroidViewModel {
    private final String LOG_TAG = PostListViewModel.class.getSimpleName();

    private final LiveData<List<PostResponse>> postListObservable;

    @Inject
    public PostListViewModel(@NonNull PostResponseRepository postResponseRepository, @NonNull LokalApplication application) {
        super(application);
        postListObservable = postResponseRepository.getPostResponseList();
    }

    public LiveData<List<PostResponse>> getPostListObservable() {
        return postListObservable;
    }

    public final PostClickWithBindingCallback postClickCallback = new PostClickWithBindingCallback() {
        @Override
        public void onClick(final PostResponse post, final PostListItemBinding binding) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(
                        getApplication(),
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PermissionChecker.PERMISSION_GRANTED) {
                    Toast
                            .makeText(getApplication(),
                                    "Storage permission not granted! Please restart app and grant permission!",
                                    Toast.LENGTH_LONG)
                            .show();

                    return;
                }
            }

            post.downloadState = PostResponse.DownloadState.DOWNLOAD_STATE_PROGRESS;
            binding.invalidateAll();


            final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplication());

            String channel_id = "LOKAL_DOWNLOAD_CHANNEL";
            CharSequence channel_name = "LOKAL_DOWNLOAD_CHANNEL";

            if (Build.VERSION.SDK_INT >= 26) {
                NotificationManager nm =
                        (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationChannel channel = new NotificationChannel(channel_id,
                        channel_name,
                        NotificationManager.IMPORTANCE_DEFAULT);
                nm.createNotificationChannel(channel);
            }

            final NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplication(), channel_id);
            builder.setContentTitle(post.filename)
                    .setContentText("Download in progress")
                    .setSmallIcon(R.drawable.ic_file_download)
                    .setPriority(NotificationCompat.PRIORITY_LOW);

            // Issue the initial notification with zero progress
            final int PROGRESS_MAX = 100;

            ImageDownloader.download(post.filename, post.post_url + "/download", new ImageDownloaderListener() {
                @Override
                public void onError(ImageDownloadError error) {
                    post.downloadState = PostResponse.DownloadState.DOWNLOAD_STATE_DEFAULT;
                    binding.invalidateAll();

                    Toast
                            .makeText(getApplication(),
                                    "Download Failed: " + error.getMessage(),
                                    Toast.LENGTH_LONG)
                            .show();
                }

                @Override
                public void onProgressChange(int percent) {
                    post.downloadProgress = percent;
                    binding.invalidateAll();

                    if(percent == 100)
                        builder.setContentText("Download complete");

                    builder.setProgress(PROGRESS_MAX, percent, false);
                    notificationManager.notify(post.id, builder.build());
                    Log.d(LOG_TAG, "onProgressChange for postid: " + post.id + "and percent: " + percent);
                }

                @Override
                public void onComplete(String path) {
                    post.downloadState = PostResponse.DownloadState.DOWNLOAD_STATE_DOWNLOADED;
                    binding.invalidateAll();

                    Toast
                            .makeText(getApplication(),
                                    "Download Complete!",
                                    Toast.LENGTH_SHORT)
                            .show();

                    builder.setContentText("Download complete")
                            .setProgress(PROGRESS_MAX, PROGRESS_MAX, false);
                    notificationManager.notify(post.id, builder.build());

                    Log.d(LOG_TAG, "onComplete for postid: " + post.id + "saved to: " + path);
                }
            });
        }
    };
}
