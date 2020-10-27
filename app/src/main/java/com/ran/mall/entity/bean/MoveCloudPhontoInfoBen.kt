package com.ran.mall.entity.bean

/**
 */

data class MoveCloudPhontoInfoBen(
        var carNumber: String = "",
        var lossId: String = "",
        var lossType: String = "",
        var _ids: MutableList<String>? = null,
        var picType: String = ""
)