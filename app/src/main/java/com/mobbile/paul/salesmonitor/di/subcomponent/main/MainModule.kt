package com.mobbile.paul.salesmonitor.di.subcomponent.main

import androidx.lifecycle.ViewModel
import com.mobbile.paul.salesmonitor.provider.ViewModelKey
import com.mobbile.paul.salesmonitor.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}