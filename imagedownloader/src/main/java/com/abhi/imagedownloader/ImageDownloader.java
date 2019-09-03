package com.abhi.imagedownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ImageDownloader {
    public static String LOG_TAG = ImageDownloader.class.getSimpleName();

    public static void download(final String filename, @NonNull final String imageUrl, final ImageDownloaderListener imageDownloadListener) {
        new AsyncTask<Void, Integer, String>() {

            private ImageDownloadError error;

            @Override
            protected void onPreExecute() {
                Log.d(LOG_TAG, "Starting download for URL: " + imageUrl);
            }

            @Override
            protected void onCancelled() {
                imageDownloadListener.onError(error);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                imageDownloadListener.onProgressChange(values[0]);
            }

            @Override
            protected String doInBackground(Void... params) {
                Bitmap bitmap;
                String filePath = null;
                HttpURLConnection connection = null;
                InputStream is = null;
                ByteArrayOutputStream out = null;
                try {
                    connection = (HttpURLConnection) new URL(imageUrl).openConnection();
                    connection.connect();

                    final int length = connection.getContentLength();
                    if (length <= 0) {
                        error = new ImageDownloadError("Invalid content length. The URL is probably not pointing to a file");
                        this.cancel(true);
                    }

                    is = new BufferedInputStream(connection.getInputStream(), 8192);
                    out = new ByteArrayOutputStream();
                    byte[] bytes = new byte[8192];
                    int count;
                    long read = 0;

                    while ((count = is.read(bytes)) != -1) {
                        read += count;
                        out.write(bytes, 0, count);
                        publishProgress((int) ((read * 100) / length));
                    }

                    bitmap = BitmapFactory.decodeByteArray(out.toByteArray(), 0, out.size());
                    filePath = saveImage(bitmap, filename);

                    if(filePath == null) {
                        error = new ImageDownloadError("Error saving image!");
                        this.cancel(true);
                    }

                } catch (Throwable e) {
                    if (!this.isCancelled()) {
                        error = new ImageDownloadError(e);
                        this.cancel(true);
                    }
                } finally {
                    try {
                        if (connection != null)
                            connection.disconnect();
                        if (out != null) {
                            out.flush();
                            out.close();
                        }
                        if (is != null)
                            is.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return filePath;
            }

            @Override
            protected void onPostExecute(String filePath) {
                if (filePath == null) {
                    Log.e(LOG_TAG, "factory returned a null result");
                    imageDownloadListener.onError(new ImageDownloadError("downloaded file could not be decoded as bitmap"));
                } else {
                    Log.d(LOG_TAG, "download complete, saved to : " + filePath);
                    imageDownloadListener.onComplete(filePath);
                }
                System.gc();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @NonNull
    private static File getDownloadDestination(String downloadSubpath) {
        File picturesFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File destinationFile = new File(picturesFolder, downloadSubpath);
        if (!destinationFile.exists())
            destinationFile.mkdirs();
        return destinationFile;
    }

    private static String saveImage(Bitmap finalBitmap, String filename) {

        File file = new File(getDownloadDestination("/Picsum/"), filename);

        if (file.exists()) file.delete();

        try {

            file.createNewFile();

            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
