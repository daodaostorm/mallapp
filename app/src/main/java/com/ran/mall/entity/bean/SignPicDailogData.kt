package com.ran.mall.entity.bean

/**
 */

data class SignPicDailogData(
        var reportId: String = "",
        var phone: String = "",
        var flowId: String = "",
        var agentId: String = "",
        var agentName: String = "",
        var pics: MutableList<SignPicBean> ?= null
)
