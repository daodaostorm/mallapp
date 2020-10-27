package com.ran.mall.entity.bean

/**
 */
data class SurveyNewReportInfo(
        var id: String = "",
        var agentId: String = "",
        var carNumber: String = "",
        var accidentDate: String = "",
        var accidentTime: String = "",
        var place: String = "",
        var reportId: String = "",
        var reasonAccid: ArrayList<String> ?= null,
        var isEarlyStage: Boolean = false,
        var makeCloudQrCode: Boolean = false,
        var accidentDetail: String = "",
        var reporter : String = "",
        var reporterCellPhone : String = "",
        var mobileCreate  : String = "",
        var reporterRelationship : String = "",
        var recognizeePhone : String = ""
)