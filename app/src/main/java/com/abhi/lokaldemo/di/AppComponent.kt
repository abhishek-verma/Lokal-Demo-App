package com.abhi.lokaldemo.di

import com.abhi.lokaldemo.LokalApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton
import android.app.Application
import dagger.BindsInstance
import dagger.android.support.DaggerApplication


@Singleton
@Component(modules=[AndroidInjectionModule::class, AppModule::class, ActivityBuilder::class])
internal interface AppComponent : AndroidInjector<LokalApplication> {

    override fun inject(app: LokalApplication)

    fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}