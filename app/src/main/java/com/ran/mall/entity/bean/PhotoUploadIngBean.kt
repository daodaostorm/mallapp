package com.ran.mall.entity.bean

import com.txt.library.base.BaseRvBean

/**
 * Created by Justin on 2018/9/5/005 14:40.
 * email：WjqJustin@163.com
 * effect：
 */
data class PhotoUploadIngBean(var imgList: List<PhotoUploadBean>, var isLocalPhotos: Boolean) : BaseRvBean()