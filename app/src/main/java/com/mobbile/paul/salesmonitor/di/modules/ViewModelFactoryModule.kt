package com.mobbile.paul.salesmonitor.di.modules

import androidx.lifecycle.ViewModelProvider
import com.mobbile.paul.salesmonitor.viewmodel.ViewModeProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactoryModule: ViewModeProviderFactory): ViewModelProvider.Factory
}