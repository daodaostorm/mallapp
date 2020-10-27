package com.ran.mall.entity.bean

import java.io.Serializable

/**
 */

data class ReportQueryInfoBean(
    val hint: String="",
    val reports: MutableList<ReportQueryOneInfo>
):Serializable

data class ReportQueryOneInfo(
    val damagedate: String="",
    val reportorname: String="",
    val licenseno: String="",
    val reportdate: String="",
    val phonenumber: String="",
    val remark: String="",
    val damagename: String="",
    val registno: String="",
    val damageaddress: String="",
    val damagehour: String="",
    val linkername: String="",
    val reportornumber: String=""
):Serializable