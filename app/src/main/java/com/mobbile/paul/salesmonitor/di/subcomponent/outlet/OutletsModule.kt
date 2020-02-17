package com.mobbile.paul.salesmonitor.di.subcomponent.outlet

import androidx.lifecycle.ViewModel
import com.mobbile.paul.salesmonitor.provider.ViewModelKey
import com.mobbile.paul.salesmonitor.ui.main.MainViewModel
import com.mobbile.paul.salesmonitor.ui.outlet.OutletViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class OutletsModule {
    @Binds
    @IntoMap
    @ViewModelKey(OutletViewModel::class)
    abstract fun bindOutletViewModel(viewModel: OutletViewModel): ViewModel
}