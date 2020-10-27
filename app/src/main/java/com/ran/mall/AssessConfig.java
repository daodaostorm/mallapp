package com.ran.mall;

/**
 * Created by DELL on 2017/10/19.
 */

public class AssessConfig {
    //是否为对外版本
    //true yds对外版本
    //false :测试版本
    public static final boolean isShowOut = false;

    //是否显示配置Dialog
    public static final boolean isShowIpDialog = false;

    public static final String PHONE_PSTN_COMMING = "pstnComing";
    public static final String PHONE_PSTN_END = "pstnEnd";


    public static final String PHONE_PSTN_COMMING_TXT = "有来电";

    public static final String PHONE_PSTN_END_TXT = "电话结束";

    public static final String CURRENTDEBUGVERSION = "-test";

    public static final String LOGNAME = "txtrecord";
    //正式域名
    public static final String rb_IP = "gdrb-yds-prod.ikandy.cn";
    public static final String rb_port = "60800";
    //    https://developer.ikandy.cn:60823
    //test域名
    public static String IP = "recorder-system-test.ikandy.cn";
    public static String port = "";
    //public static String IP = "developer.ikandy.cn";
    //public static String port = "60726";
    //public static String IP = "192.168.1.65";
    //public static String port = "60913";
//    public static String IP = "52.80.140.49"; //https://52.80.140.49:60726
//    public static String port = "60726";
    //默认登录账号 （debug模式）
//    public static final String DEBUG_LOGINNAME = "zyyt3";
    public static final String DEBUG_LOGINNAME = "test1";
    public static final String DEBUG_LOGINPWD = "123456";

    public static final String OCRURL = "https://ocr-api.ikandy.cn/ocr/card";
    public static final String OCRKEY = "TxthRD2xvdz5Ds2YkDH";
    public static final String OCRSECRET = "Rz5DkY2xvdWXhDHs2Y";


}
