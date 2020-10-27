package com.ran.mall.entity.bean

import java.io.Serializable

/**
 * Created by Justin on 2018/12/26/026 10:52.
 * email：WjqJustin@163.com
 * effect：
 */
data class ReportQueryBean(
    val hint: String="",
    val reports: MutableList<Report>
):Serializable

data class Report(
    val processInfo: ProcessInfo,
    val registInfo: RegistInfo
):Serializable

data class RegistInfo(
    val damageaddress: String="",
    val licenseno: String="",
    val linkername: String="",
    val registno: String="",
    val remark: String="",
    val reportdate: String="",
    val reportorname: String="",
    val thirdlicenseno: String=""
):Serializable

data class ProcessInfo(
//    val adjust: Adjust,
    val check: Check,
    val loss: Loss,
    val regist: Regist
):Serializable

//data class Adjust(
//    val remark: String //todo
//)

data class Check(
    val date: String=""
):Serializable

data class Regist(
    val date: String=""
):Serializable

data class Loss(
    val date: String="",
    val money: String="",
    val type: String="",
    val detail: LossDetail
):Serializable

data class LossDetail(
        val component: LossDetailComponent,
        val manHour: LossDetailmanHour,
        val material: LossDetailmaterial,
        val remnant: LossDetailremnant
):Serializable

data class LossDetailComponent(
        val list: MutableList<LossDetailListInfo>,
        val sum: String=""
):Serializable

data class LossDetailmanHour(
        val list: MutableList<LossDetailListInfo>,
        val sum: String=""
):Serializable

data class LossDetailmaterial(
        val list: MutableList<LossDetailListInfo>,
        val sum: String=""
):Serializable

data class LossDetailremnant(
        val sum: String=""
):Serializable

data class LossDetailListInfo(
        val name: String="",
        val money: String="",
        val count: String=""
):Serializable
