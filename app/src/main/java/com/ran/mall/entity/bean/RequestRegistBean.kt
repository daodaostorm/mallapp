package com.ran.mall.entity.bean

import java.io.Serializable
/**
 */

data class RequestRegistBean(
        var username: String = "",
        var password: String = "",
        var phone: String = "",
        var question: String = "",
        var answer: String = ""
):Serializable