package com.ran.mall.entity.bean

/**
 */



data class EssayInfoListBean(
        var content: ArrayList<EssayInfo> ?= null
)

data class EssayInfo(
        var id: Long = 0,
        var proid: String = "",
        var essayid: String = "",
        var name: String = "",
        var subtitle: String = "",
        var author: String = "",
        var detailpic1: String = "",
        var detailpic2: String = "",
        var detailpic3: String = "",
        var detailpic4: String = "",
        var detailtext1: String = "",
        var detailtext2: String = "",
        var detailtext3: String = "",
        var detailtext4: String = "",
        var status: Int = 0
)

