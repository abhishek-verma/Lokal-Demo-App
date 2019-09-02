package com.abhi.lokaldemo.di;

import android.app.Application;
import android.content.Context;

import com.abhi.lokaldemo.LokalApplication;
import com.abhi.lokaldemo.api.repo.PicSumService;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class AppModule {

    @Binds
    abstract Context provideContext(LokalApplication application);

    @Singleton
    @Provides
    PicSumService provideGithubService() {
        return new Retrofit.Builder()
                .baseUrl(PicSumService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PicSumService.class);
    }
}
