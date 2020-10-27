package com.ran.mall.entity.bean

/**
 */



data class DeviceWifiInfo(
        var wifiName: String = "",
        var wifiAddress: String = "",
        var wifiPassWord: String = "",
        var wifiCmd: String = "IDLE",
        var connectStatus: Boolean = false
)
