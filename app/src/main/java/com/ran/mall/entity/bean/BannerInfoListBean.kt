package com.ran.mall.entity.bean

/**
 */



data class BannerInfoListBean(
        var content: ArrayList<BannerInfo> ?= null
)

data class BannerInfo(
        var proid: String = "",
        var title: String = "",
        var picUrl: String = "",
        var status: Int = 0
)

