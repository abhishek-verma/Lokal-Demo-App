package com.abhi.lokaldemo.di;

import com.abhi.lokaldemo.ui.main.MainActivity;
import com.abhi.lokaldemo.ui.main.MainActivityModule;
import com.abhi.lokaldemo.ui.main.MainFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    abstract MainActivity bindMainActivity();

}
