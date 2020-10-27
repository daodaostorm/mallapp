package com.ran.mall.entity.bean

import java.io.Serializable

/**
 */

data class ReportQueryReportInfoBean(
    val hint: String="",
    val report: ReportQueryReportInfoReport
):Serializable

data class ReportQueryReportInfoReport(
        val registInfo: ReportQueryReportInfoReportRegist
):Serializable

data class ReportQueryReportInfoReportRegist(
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