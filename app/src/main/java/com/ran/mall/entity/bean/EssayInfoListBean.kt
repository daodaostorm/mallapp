package com.ran.mall.entity.bean

/**
 */



data class EssayInfoListBean(
        var content: ArrayList<EssayInfo> ?= null
)

data class EssayInfo(
        var id: Long = 0,
        var proid: String = "",
        var cateid: String = "",
        var name: String = "",
        var subtitle: String = "",
        var mainimage: String = "",
        var detailpic1: String = "",
        var detailpic2: String = "",
        var detailpic3: String = "",
        var detailpic4: String = "",
        var detail: String = "",
        var price: Float = 0F,
        var orginprice: Float = 0F,
        var recommend: Int = 0,
        var stock: Int = 0,
        var status: Int = 0
)

