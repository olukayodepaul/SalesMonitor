package com.mobbile.paul.salesmonitor.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobbile.paul.salesmonitor.model.PostToServer
import com.mobbile.paul.salesmonitor.model.PostToServerResponse
import com.mobbile.paul.salesmonitor.provider.Repository
import javax.inject.Inject

class MainViewModel @Inject constructor(private var repo: Repository): ViewModel() {

    fun moveQuestionareToServer(data: PostToServer) : MutableLiveData<PostToServerResponse> {
        val result = MutableLiveData<PostToServerResponse>()
        repo.fetchPostSales(data)
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