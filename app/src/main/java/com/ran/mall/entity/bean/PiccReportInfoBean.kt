package com.ran.mall.entity.bean

import java.io.Serializable

/**
 */

data class PiccReportInfoBean(
        val errCode: String="",
        val registInfo: PiccRegistInfo
):Serializable

data class PiccRegistInfo(
    val damageaddress: String="",
    val licenseno: String="",
    val damagedate: String="",
    val damagehour: String="",
    val damagename: String="",
    val linkername: String="",
    val registno: String="",
    val remark: String="",
    val reportdate: String="",
    val reportorname: String="",
    val thirdCarInfo: MutableList<ThirdCarDetail>
):Serializable


//data class Adjust(
//    val remark: String //todo
//)

data class ThirdCarDetail(
    val licenseno: String="",
    val linkphone: String=""
):Serializable

