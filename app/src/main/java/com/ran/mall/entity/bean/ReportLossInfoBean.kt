package com.ran.mall.entity.bean

import java.io.Serializable

/**
 */

data class ReportLossInfoBean(
    val vinno: String="",
    val money: String="",
    val veriMoney: String="",
    val component: Component,
    val manHour: ManHour,
    val material: Material
):Serializable

data class Component(
        val sum: String="",
        val list: MutableList<LossDetailInfo>
):Serializable

data class ManHour(
    val sum: String="",
    val list: MutableList<LossDetailInfo>
):Serializable

data class Material(
        val sum: String="",
        val list: MutableList<LossDetailInfo>
):Serializable

data class ManHourDetailInfo(
    val name: String="",
    val money: String=""
):Serializable

data class LossDetailInfo(
        val name: String="",
        val money: String="",
        val count: Int=-1
):Serializable

data class ComponentDetailInfo(
        val name: String="",
        val money: String="",
        val count: Int=-1
):Serializable

data class MaterialDetailInfo(
        val name: String="",
        val money: String="",
        val count: Int
):Serializable