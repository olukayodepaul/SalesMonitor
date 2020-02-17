package com.mobbile.paul.salesmonitor.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PostToServer(

    var outletclass: String = "",
    @SerializedName("groupbuttonseenoutlet")
    @Expose
    var groupbuttonseenoutlet: String = "",
    @SerializedName("groupButtonnotseenoutlet")
    @Expose
    var groupButtonnotseenoutlet: String = "",
    @SerializedName("customeruyingfrom")
    @Expose
    var customeruyingfrom: String = "",
    @SerializedName("custsatisfaction")
    @Expose
    var custsatisfaction: String = "",
    @SerializedName("smstoken")
    @Expose
    var smstoken: String = "",
    @SerializedName("totalvolume")
    @Expose
    var totalvolume: String = "",
    @SerializedName("remarks")
    @Expose
    var remarks: String = "",
    @SerializedName("supervisorid")
    @Expose
    var supervisorid: Int = 0,
    @SerializedName("urno")
    @Expose
    var urno: Int = 0,
    @SerializedName("repid")
    @Expose
    var repid: Int = 0
)

data class PostToServerResponse(
    @SerializedName("status")
    @Expose
    var status: Int = 0,
    @SerializedName("notis")
    @Expose
    var notis: String = ""
)

data class Outlets_Parent_Model(
    @SerializedName("status")
    @Expose
    var status: Int = 0,
    @SerializedName("all_outlets")
    @Expose
    var all_outlets: List<Outlets_Child_Model> = emptyList()
)

data class Outlets_Child_Model(
    //on press down to bring out the other information of the outlet
    //using material dialog
    @SerializedName("urno")
    @Expose
    var urno: Int = 0,
    @SerializedName("volumeclass")
    @Expose
    var volumeclass: String = "",
    @SerializedName("outletname")
    @Expose
    var outletname: String = "",
    @SerializedName("outletaddress")
    @Expose
    var outletaddress: String = "",
    @SerializedName("contactphone")
    @Expose
    var contactphone: String = ""
)

data class Sales_Rep_Parent_Model(
    @SerializedName("status")
    @Expose
    var status: Int = 0,
    @SerializedName("all_sales_rep")
    @Expose
    var all_sales_rep: List<Sales_Rep_Child_Model> = emptyList()
)

data class Sales_Rep_Child_Model (
    @SerializedName("repname")
    @Expose
    var repname: String = "",
    @SerializedName("rep_edcode")
    @Expose
    var rep_edcode: String = "",
    @SerializedName("rep_id")
    @Expose
    var rep_id: Int = 0
)

data class LoginResponse (
    @SerializedName("status")
    @Expose
    var status: Int = 0,
    @SerializedName("messages")
    @Expose
    var messages: String = "",
    @SerializedName("notification")
    @Expose
    var notification: String = "",
    @SerializedName("supervisor_id")
    @Expose
    var supervisor_id: Int = 0,
    @SerializedName("region_id")
    @Expose
    var region_id: Int = 0,
    @SerializedName("depots_id")
    @Expose
    var depots_id: Int = 0
)



