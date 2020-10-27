package com.ran.mall.entity.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "localphoto", indices = arrayOf(Index(value = "photo_path"), Index(value = "photo_systemTime")))
class LocalPhotoEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "photo_path")
    var path: String = ""

    @ColumnInfo(name = "photo_success")
    var photoSuccess: String = ""

    @ColumnInfo(name = "photo_source")//图片来源 视频 拍照
    var photoSource: String = ""

    @ColumnInfo(name = "photo_systemTime")
    var photoSystemTime: String = ""



}
