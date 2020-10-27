package com.ran.mall.entity.bean

import com.inuker.bluetooth.library.search.SearchResult
import java.io.Serializable

/**
 */

data class BlueToothSearchBean(
    var resultDevice: SearchResult ?= null,
    var isChecked: Boolean= false
):Serializable
