package com.ran.mall.entity.bean

/**
 */



data class VideoListInfo(
        var name: String = "",
        var reportId: String = "",
        var recordedAt: Long = 0,
        var macAddress: String = "",
        var starttime: String = "",
        var lengthtime: String = "",
        var size: String = "",
        var process: Int = 0,
        var status: Int = 0
)
