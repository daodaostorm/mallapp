package com.ran.mall.entity.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "historyreport", indices = [(Index(value = "systemTime")), (Index(value = ("historydiscipline"), unique = true))])
class HistoryReportEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "historydiscipline")
    var historydiscipline: String = ""

    @ColumnInfo(name = "systemTime")
    var systemTime: String = ""
}