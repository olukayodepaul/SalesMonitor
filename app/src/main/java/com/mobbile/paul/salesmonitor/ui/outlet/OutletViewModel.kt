package com.mobbile.paul.salesmonitor.ui.outlet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobbile.paul.salesmonitor.model.Outlets_Parent_Model
import com.mobbile.paul.salesmonitor.provider.Repository
import javax.inject.Inject

class OutletViewModel @Inject constructor(private var repo: Repository): ViewModel(){

    fun getAllCustomersDetails(employee_id: Int) : LiveData<Outlets_Parent_Model> {
        val result = MutableLiveData<Outlets_Parent_Model>()
        repo.getAllCustomersDetails(employee_id)
            .subscribe({
                Log.d("TAG", it.body().toString())
                result.postValue(it.body())
            },{
                result.postValue(null)
            }).isDisposed
        return result
    }

    companion object{
        val TAG = "JDCDJCUJDJDN"
    }

}