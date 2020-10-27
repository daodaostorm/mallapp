package com.ran.mall.entity.db

import com.ran.mall.entity.db.entity.HistoryReportEntity

/**
 * Created by Justin on 2018/8/23/023 14:14.
 * email：WjqJustin@163.com
 * effect：
 */
interface DatabaseCallback1 {

    fun getHistoryReport(entities: List<HistoryReportEntity>)

    fun onAdded()

     fun onError(error: String)

}