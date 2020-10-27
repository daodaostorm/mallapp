package com.ran.mall.entity.bean

/**
 * Created by JustinWjq
 * @date 2019/5/14.
 * description：
 */
data class CarInfoReportItem(
        var registno: String = "",
        var licenseno: String,
        var damagedate: String,
        var damagehour: String,
        var damageaddress: String,
        var remark: String,
        var reportorname : String,
        var reportornumber : String,
        var reasonAccid: ArrayList<String> ?= null,
        var relationship : String = "000"

)