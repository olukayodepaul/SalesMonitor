package com.mobbile.paul.salesmonitor.di.subcomponent.login

import androidx.lifecycle.ViewModel
import com.mobbile.paul.salesmonitor.provider.ViewModelKey
import com.mobbile.paul.salesmonitor.ui.login.LoginViewModel
import com.mobbile.paul.salesmonitor.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LoginModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel
}