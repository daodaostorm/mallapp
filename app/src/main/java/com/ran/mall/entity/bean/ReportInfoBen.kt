package com.ran.mall.entity.bean

/**
 * Created by DELL on 2017/10/20.
 */

data class ReportInfoBen(
        var carNumber: String = "",
//        var place: String = "",
//        var message: String = "",
        var carSource: Int = 0,
        var missionType: String = "lossEstimate",
        var caseType: String = "",
        var reportId: String = "",
        var accountId: String = "",
//        var flowId: String = "",
        var directStorePay: Boolean = true, //是否到店直赔
        var reporterRelationship: String = "非本人", //报案人与被保险人关系
        var reporterCellPhone: String = "", // 报案人手机
        var idCard: String = "", // 身份证
        var accidentDate: String = "",// 出险日期
        var reporter: String = ""
)
