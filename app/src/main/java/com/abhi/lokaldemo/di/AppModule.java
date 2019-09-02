package com.abhi.lokaldemo.di;

import android.app.Application;
import android.content.Context;

import com.abhi.lokaldemo.LokalApplication;
import com.abhi.lokaldemo.api.repo.PicSumService;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Provides
    Context provideContext(LokalApplication application) {
        return application;
    }

    @Singleton
    @Provides
    PicSumService provideGithubService() {

        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logger)
                .build();

        return new Retrofit.Builder()
                .baseUrl(PicSumService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PicSumService.class);
    }
}
