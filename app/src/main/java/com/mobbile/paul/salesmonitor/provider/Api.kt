package com.mobbile.paul.salesmonitor.provider

import com.mobbile.paul.salesmonitor.model.*
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {

    @Headers("Connection:close")
    @POST("/api/postquestionnaire")
    fun postToServer(
        @Body datas: PostToServer
    ): Single<Response<PostToServerResponse>>


    @Headers("Connection:close")
    @POST("/api/supervisor_reps_list")
    fun getRepDetails(
        @Query("region_id") region_id: Int,
        @Query("depot_id") depot_id: Int
    ): Single<Response<Sales_Rep_Parent_Model>>

    @Headers("Connection:close")
    @POST("/api/supervisor_customers_list")
    fun getAllCustomersDetails(
        @Query("employee_id") employee_id: Int
    ): Single<Response<Outlets_Parent_Model>>

    @Headers("Connection:close")
    @POST("/api/supervisors_login")
    fun setLogins(
        @Query("username") username: String,
        @Query("password") password: String
    ): Single<Response<LoginResponse>>

}


