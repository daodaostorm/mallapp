package com.ran.mall.entity.bean

import java.io.Serializable

/**
 */


data class CloutPhotoBeanList(
        val clouds: MutableList<CloutPhotoBean>
):Serializable

data class CloutPhotoBean(
                           var id: String = "",
                           var uploadedToDZ: String = "",
                           var uploadedToCarLoss: String = "",
                           var thumbnail_url: String = "",
                           var original_url: String = ""
) : Serializable