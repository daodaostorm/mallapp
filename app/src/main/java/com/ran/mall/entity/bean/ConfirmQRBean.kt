package com.ran.mall.entity.bean

/**
 */

data class ConfirmQRResultBean(
        var allowTx: Boolean = true,
        var isTx: Boolean = false,
        var data: MutableList<ConfirmQRBean> ?= null
)


data class ConfirmQRBean(
        var lossType: String = "",
        var carNumber: String = "",
        var qrType: Int = 0,
        var image: String = ""
)
