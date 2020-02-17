package com.mobbile.paul.salesmonitor.di.subcomponent.salesrep

import androidx.lifecycle.ViewModel
import com.mobbile.paul.salesmonitor.provider.ViewModelKey
import com.mobbile.paul.salesmonitor.ui.main.MainViewModel
import com.mobbile.paul.salesmonitor.ui.outlet.OutletViewModel
import com.mobbile.paul.salesmonitor.ui.salesrep.SalesRepViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SalesRepModule {
    @Binds
    @IntoMap
    @ViewModelKey(SalesRepViewModel::class)
    abstract fun bindSalesRepViewModel(viewModel: SalesRepViewModel): ViewModel
}