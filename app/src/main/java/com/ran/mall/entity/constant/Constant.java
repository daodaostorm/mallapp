package com.ran.mall.entity.constant;


import com.ran.mall.AssessConfig;

public class Constant {


    public static class PermissionMode {
        public static final int READ_PHONE_STATE = 100;
        public static final int READ_EXTERNAL_STORAGE = 101;
        public static final int WRITE_EXTERNAL_STORAGE = 102;
    }

    public final static int REQUEST_CODE_SEARCH_BINDAGENT = 101;



    public final static String SEARCH_TYPE = "search_type";

    public final static String ISLOCAL = "isLocal";
    public final static String ISCARD = "isCard";
    public final static String REPORTID = "reportId";
    public final static String PHOTO_ID = "PHOTO_ID";
    public final static String PHOTO_LOSSTYPE = "PHOTO_LOSSTYPE";
    public final static String NAME = "name";
    public final static String AGENTID = "agentid";
    public final static String FLOWID = "flowid";
    public final static String CARNUMBER = "carnumber";
    public final static String DATETIME = "datetime";

    public final static String NEWTASK = "newtask";

    public final static String TASKTYPE = "tasktype";

    public final static int TASKTYPE_CAR = 0;
    public final static int TASKTYPE_MAN = 1;
    public final static int TASKTYPE_THING = 2;

    public static final String ORG_NAME = "orgName"; //组织名
    public static final String IS_CALL = "isCall"; //是否允许视频和照片上传
    public static final String IS_TASKDETAIL = "isTaskdetail"; //是否跳转任务详情页
    public static final String Phone = "phone"; //是否允许视频和照片上传
    public static final String State = "state"; //工单状态

    public static boolean KANDY_ISLOGIN = false; //全局的kandy登录状态

    public static class OCRType {
        public static final String idcard = "idcard";
        public static final String driving = "driving";
        public static final  String vehicle = "vehicle";
        public static final String bankcard = "bankcard";
        public static final String license_plate = "license_plate";
        public static final String vin = "vin";
    }

    public static String getUrlFullPath(String strUrl){
        String strFullPath = "";
        if (AssessConfig.isShowOut){
            strFullPath = "https://yds.s3.cn-north-1.amazonaws.com.cn/" + strUrl;
        } else {
            strFullPath = "https://gdrb-dingsun-test.s3.cn-north-1.amazonaws.com.cn/" + strUrl;
        }
        return strFullPath;
    }
}
