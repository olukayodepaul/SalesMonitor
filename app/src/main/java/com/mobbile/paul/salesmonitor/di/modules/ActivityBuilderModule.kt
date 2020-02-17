package com.mobbile.paul.salesmonitor.di.modules

import com.mobbile.paul.salesmonitor.di.subcomponent.login.LoginModule
import com.mobbile.paul.salesmonitor.di.subcomponent.login.LoginScope
import com.mobbile.paul.salesmonitor.di.subcomponent.main.MainModule
import com.mobbile.paul.salesmonitor.di.subcomponent.main.MainScope
import com.mobbile.paul.salesmonitor.di.subcomponent.outlet.OutletsModule
import com.mobbile.paul.salesmonitor.di.subcomponent.outlet.OutletsScope
import com.mobbile.paul.salesmonitor.di.subcomponent.salesrep.SalesRepModule
import com.mobbile.paul.salesmonitor.di.subcomponent.salesrep.SalesRepScope
import com.mobbile.paul.salesmonitor.ui.login.Login
import com.mobbile.paul.salesmonitor.ui.main.MainActivity
import com.mobbile.paul.salesmonitor.ui.outlet.Outlets
import com.mobbile.paul.salesmonitor.ui.salesrep.SalesRep
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilderModule {

    @MainScope
    @ContributesAndroidInjector(
        modules = [
            MainModule::class
        ]
    )
    abstract fun contributeActivityAndroidInjector(): MainActivity

    @LoginScope
    @ContributesAndroidInjector(
        modules = [
            LoginModule::class
        ]
    )
    abstract fun contributeLoginAndroidInjector(): Login

    @OutletsScope
    @ContributesAndroidInjector(
        modules = [
            OutletsModule::class
        ]
    )
    abstract fun contributeOutletsAndroidInjector(): Outlets

    @SalesRepScope
    @ContributesAndroidInjector(
        modules = [
            SalesRepModule::class
        ]
    )
    abstract fun contributeSalesRepAndroidInjector(): SalesRep
}