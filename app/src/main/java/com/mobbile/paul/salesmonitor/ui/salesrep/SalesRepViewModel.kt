package com.mobbile.paul.salesmonitor.ui.salesrep

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobbile.paul.salesmonitor.model.Sales_Rep_Parent_Model
import com.mobbile.paul.salesmonitor.provider.Repository
import javax.inject.Inject

class SalesRepViewModel @Inject constructor(private var repo: Repository): ViewModel() {

    fun getRepDetails(region_id:Int, depot_id:Int) : LiveData<Sales_Rep_Parent_Model> {
        val result = MutableLiveData<Sales_Rep_Parent_Model>()
        repo.getRepDetails(region_id, depot_id)
            .subscribe({
                result.postValue(it.body())
            },{
                result.postValue(null)
            }).isDisposed
        return result
    }

}