package com.ran.mall.entity.bean

import java.io.Serializable

/**
 */

data class UserBean(
        val token: String="",
        var agent: Agent ?= null
):Serializable

data class Agent(
    val _id: String = "",
    val loginName: String="",
    val fullName: String="",
    val cellphone: String="",
    val deviceID: String=""
):Serializable

