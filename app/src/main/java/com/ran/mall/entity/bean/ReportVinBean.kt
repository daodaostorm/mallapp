package com.ran.mall.entity.bean

import java.io.Serializable

/**
 */

data class ReportVinBean(
    val registInfo: ReportVinRegistInfo,
    val processInfo: ReportProcessInfo
):Serializable

data class ReportVinRegistInfo(
        val damagedate: String="",
        val reportorname: String="",
        val reportdate: String="",
        val phonenumber: String="",
        val licenseno: String="",
        val remark: String="",
        val damagename: String="",
        val registno: String="",
        val damageaddress: String="",
        val damagehour: String="",
        val linkername: String="",
        val reportornumber: String="",
        val relationship: String="",
        val vinno: String=""
):Serializable

data class ReportProcessInfo(
        val regist: ReportRegistInfo,
        val check: ReportCheckInfo,
        val loss: MutableList<ReportOneLossInfo>
):Serializable

data class ReportRegistInfo(
        val name: String="",
        val date: String=""
):Serializable

data class ReportCheckInfo(
        val name: String="",
        val date: String=""
):Serializable

data class ReportOneLossInfo(
        val name: String="",
        val date: String="",
        val carNumber: String="",
        val veriMoney: String="",
        val money: String="",
        val vinno: String=""
):Serializable