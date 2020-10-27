package com.ran.mall.entity.bean

/**
 */



data class TaskDetailInfo(
        var agent: String = "",
        var insurance: String = "",
        var ctime: String = "",
        var flowId: String = "",
        var fields: FieldsInfo ?= null
)
