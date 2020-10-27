package com.ran.mall.entity.bean

import java.io.Serializable

/**
 * Created by Justin on 2018/9/5/005 14:40.
 * email：WjqJustin@163.com
 * effect：
 */
data class PhotoUploadBean(var imgpath: String,
                           var originalImg: String,
                           var uploadstatus: String,
                           var systemTime: String,
                           var isLocal: Boolean,
                           var uploaded: Boolean = false,
                           var dsUploading: Boolean = false,
                           var id: String ="",
                           var isCard: String = "carPics",
                           var index: Int = 0
) : Serializable