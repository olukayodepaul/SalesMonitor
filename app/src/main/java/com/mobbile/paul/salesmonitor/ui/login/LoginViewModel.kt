package com.mobbile.paul.salesmonitor.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobbile.paul.salesmonitor.model.LoginResponse
import com.mobbile.paul.salesmonitor.provider.Repository
import javax.inject.Inject

class LoginViewModel @Inject constructor(private var repo: Repository): ViewModel() {

    fun setLogins(username: String, password:String) : MutableLiveData<LoginResponse> {
        val result = MutableLiveData<LoginResponse>()
        repo.setLogins(username, password)
            .subscribe(
                {
                    result.postValue(it.body())
                },{
                    result.postValue(null)
                }
            ).isDisposed
        return result
    }
}