package com.ran.mall.entity.bean

import java.io.Serializable

/**
 */

data class RecordDeviceInfo(
        var wifiinfo: RecordDeviceWifiInfo ?= null,
        var recordStatus: RecordStatusInfo ?= null,
        var recordSpace: String = "0",
        var recordPower: String = "0",
        var recordBlueToothMac: String = "0",
        var uploadset: String = "0"
):Serializable

data class RecordDeviceWifiInfo (
        var wifiname: String = "",
        var wifipass: String = "",
        var wifiip: String = "",
        var wifimac: String = "",
        var connected: String = "0"
):Serializable

data class RecordStatusInfo (
        var recordInfo: String = "0",
        var videoAddr: String = "0",
        var audioAddr: String = "0",
        var statusInfo: String = "0",
        var recordId: String = ""
):Serializable
