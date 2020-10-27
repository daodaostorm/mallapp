package com.ran.mall.entity.bean

/**
 */



data class TaskInfoBean(
        var agent: String = "",
        var insurance: String = "",
        var fields: FieldsInfo ?= null
)

data class FieldsInfo(
        var reportId: String = "",
        var insurantName: String = "",
        var carNumber: String = "",
        var reportPlace: String = "",
        var suveryPlace: String = "",
        var remark: String = "",
        var damageType: ArrayList<String> ?= null,
        var reportDetail: String = ""
)