package com.ran.mall.entity.bean

import java.io.Serializable

/**
 */

data class AddressInfoBean(
    var userid: String="",
    var receiverprovince: String="",
    var receivercity: String="",
    var receiverdistrict: Double=0.0,
    var receiveraddress: Double=0.0,
    var receivername: String="",
    var receiverphone: String="",
    var label: String=""
):Serializable
