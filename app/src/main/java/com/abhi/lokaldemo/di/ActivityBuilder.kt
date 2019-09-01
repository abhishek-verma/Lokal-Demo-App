package com.abhi.lokaldemo.di

import dagger.Module
import com.abhi.lokaldemo.ui.main.MainActivity
import com.abhi.lokaldemo.ui.main.MainActivityModule
import dagger.android.ContributesAndroidInjector



@Module
abstract class ActivityBuilder {


    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun bindMainActivity(): MainActivity
}