package com.ran.mall.entity.bean

import java.io.Serializable

/**
 */

data class PairedBlueToothInfoBean(
    var name: String="",
    var macAddress: String="",
    var wifiInfo: DeviceWifiInfo ?= null
):Serializable
