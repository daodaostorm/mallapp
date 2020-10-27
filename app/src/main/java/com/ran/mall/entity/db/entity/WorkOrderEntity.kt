package com.ran.mall.entity.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "workorder", indices = arrayOf(Index(value = ("userName")), Index(value = ("flowId"), unique = true)))
class WorkOrderEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "userName")
    var userName: String=""

    @ColumnInfo(name = "flowId")
    var flowId: String=""


}
