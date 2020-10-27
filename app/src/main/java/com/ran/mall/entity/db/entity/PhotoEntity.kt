package com.ran.mall.entity.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "photo", indices = arrayOf(Index(value = "photo_flowId"), Index(value = "photo_systemTime")))
class PhotoEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "userName")
    var userName: String = ""

    @ColumnInfo(name = "photo_flowId")
    var flowId: String = ""

    @ColumnInfo(name = "photo_path")
    var path: String = ""

    @ColumnInfo(name = "photo_status")//图片类型 单证
    var photoStatus: String = ""

    @ColumnInfo(name = "photo_source")//图片来源 视频 相机
    var photoSource: String = ""

    @ColumnInfo(name = "photo_carnumber")
    var photoCarnumber: String = ""

    @ColumnInfo(name = "photo_success")
    var photoSuccess: String = ""

    @ColumnInfo(name = "photo__id")
    var photo_id: String = ""

    @ColumnInfo(name = "photo_lossType")
    var photo_lossType: String = ""


    @ColumnInfo(name = "photo_systemTime")
    var photoSystemTime: String = ""



}
