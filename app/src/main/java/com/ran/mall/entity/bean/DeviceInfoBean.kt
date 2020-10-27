package com.ran.mall.entity.bean

import java.io.Serializable

/**
 */

data class DeviceInfoBean(
    var agentId: String="",
    var macAddress: String="",
    var place: String="",
    var latitude: Double=0.0,
    var longitude: Double=0.0,
    var status: String="",
    var storageAll: String="",
    var storageRest: String="",
    var power: String=""
):Serializable
