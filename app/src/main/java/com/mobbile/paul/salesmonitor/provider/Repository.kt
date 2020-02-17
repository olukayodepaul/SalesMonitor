package com.mobbile.paul.salesmonitor.provider


import com.mobbile.paul.salesmonitor.model.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject
constructor(private val api: Api) {

    //rxjava move to korotin
    fun fetchPostSales(data: PostToServer): Single<Response<PostToServerResponse>> =
        api.postToServer(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}

    fun getRepDetails(region_id:Int, depot_id:Int): Single<Response<Sales_Rep_Parent_Model>> =
        api.getRepDetails(region_id, depot_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}

    fun getAllCustomersDetails(employee_id: Int): Single<Response<Outlets_Parent_Model>> =
        api.getAllCustomersDetails(employee_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}

    fun setLogins(username:String, password:String): Single<Response<LoginResponse>> =
        api.setLogins(username,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {it}
}