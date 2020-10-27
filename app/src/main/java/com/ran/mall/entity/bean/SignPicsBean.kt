package com.ran.mall.entity.bean

/**
 */

data class SignPicsBean(
        var reportId: String = "",
        var pics: MutableList<SignPicBean> ?= null
)


data class SignPicBean(
        var needResign: Boolean = false,
        var lossId: String = "",
        var lossType: String = "",
        var carNumber: String = "",
        var signUrl: String = ""
)
