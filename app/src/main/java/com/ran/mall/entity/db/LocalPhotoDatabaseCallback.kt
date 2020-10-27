package com.ran.mall.entity.db

import com.ran.mall.entity.db.entity.LocalPhotoEntity


/**
 */
interface LocalPhotoDatabaseCallback {

    fun onAddPhotoed()

    fun onDeletePhoto()

    fun onUpDate()

    fun onError(err: String)

    fun onQuery()

    fun onLoadPhotoPath(paths: List<LocalPhotoEntity>)

}