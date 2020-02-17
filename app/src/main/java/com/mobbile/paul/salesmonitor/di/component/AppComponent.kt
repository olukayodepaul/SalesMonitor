package com.mobbile.paul.salesmonitor.di.component

import android.app.Application
import com.mobbile.paul.salesmonitor.BaseApplication
import com.mobbile.paul.salesmonitor.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuilderModule::class,
    ApiModule::class,
    NetworkModule::class,
    LocalCacheModule::class,
    ViewModelFactoryModule::class
])
interface AppComponent: AndroidInjector<BaseApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}