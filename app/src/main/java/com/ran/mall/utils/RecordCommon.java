package com.ran.mall.utils;


import com.ran.mall.entity.bean.DeviceWifiInfo;
import com.ran.mall.entity.bean.RecordDeviceInfo;
import com.ran.mall.entity.bean.RecordDeviceWifiInfo;
import com.ran.mall.entity.bean.RecordStatusInfo;

import java.util.ArrayList;

public class RecordCommon {

    private static final Object locker = new Object();

    private static RecordCommon instance = null;
    private static RecordDeviceInfo recordDeviceInfo = null;

    public static int VALUE_TYPE_STATUS = 0;
    public static int VALUE_TYPE_RESULT = 1;
    public static int VALUE_TYPE_WIFILIST_RESULT = 2;
    public static int VALUE_TYPE_WIFIINFO_RESULT = 3;

    public static String RESULT_TYPE_RECORD_STATUS = "RSTA";
    public static String RESULT_TYPE_RECORD_RESULT = "RSRE";

    public static String RESULT_TYPE_WIFILIST = "WIST";
    public static String RESULT_TYPE_WIFIINFO = "WIFO";

    public static RecordCommon getInstance() {
        if (instance == null) {
            synchronized (locker) {
                if (instance == null) {
                    instance = new RecordCommon();
                    recordDeviceInfo = new RecordDeviceInfo();
                }
            }
        }
        return instance;
    }

    public RecordDeviceWifiInfo strToWifiInfo(String strInfo) {
        RecordDeviceWifiInfo wifiInfo = new RecordDeviceWifiInfo();

        if (strInfo != null) {

            if (strInfo.startsWith("0")) {
                return wifiInfo;
            } else {
                String[] strArr = strInfo.split("&");

                for (int i = 0; i < strArr.length; i++) {

                    if (strArr[i].startsWith("NAME")) {
                        wifiInfo.setWifiname(strArr[i].substring(5));
                    } else if (strArr[i].startsWith("PASS")) {
                        wifiInfo.setWifipass(strArr[i].substring(5));
                    } else if (strArr[i].startsWith("STATUS")) {
                        wifiInfo.setConnected(strArr[i].substring(7));
                    } else if (strArr[i].startsWith("IP")) {
                        wifiInfo.setWifiip(strArr[i].substring(3));
                    } else if (strArr[i].startsWith("MAC")) {
                        wifiInfo.setWifimac(strArr[i].substring(4));
                    }
                }
            }
        }

        return wifiInfo;
    }

    public int checkValueType(String strInfo){
        int valueType = VALUE_TYPE_STATUS;

        if (strInfo.startsWith(RESULT_TYPE_RECORD_STATUS)){
            valueType = VALUE_TYPE_STATUS;
        } else if (strInfo.startsWith(RESULT_TYPE_RECORD_RESULT)){
            valueType = VALUE_TYPE_RESULT;
        } else if (strInfo.startsWith(RESULT_TYPE_WIFILIST)){
            valueType = VALUE_TYPE_WIFILIST_RESULT;
        } else if (strInfo.startsWith(RESULT_TYPE_WIFIINFO)){
            valueType = VALUE_TYPE_WIFIINFO_RESULT;
        }

        return valueType;
    }


    public ArrayList<DeviceWifiInfo> strToWifiListInfo(String strInfo) {
        ArrayList<DeviceWifiInfo> listInfo = new ArrayList<DeviceWifiInfo>();
        listInfo.clear();
        int listNum = 0;
        boolean isExist = false;

        if (strInfo != null) {
            LogUtils.i("strToWifiListInfo:" + strInfo);
            if (strInfo.startsWith(RESULT_TYPE_WIFILIST)) {
                String[] strArr = strInfo.substring(5).split("#");
                for (int i = 0; i < strArr.length; i++) {
                    DeviceWifiInfo info = new DeviceWifiInfo();
                    info.setWifiName(strArr[i]);
                    listNum = listInfo.size();
                    isExist = false;
                    for (int j = 0; j < listNum; j++){
                        if (listInfo.get(j).getWifiName().endsWith(info.getWifiName())){
                            isExist = true;
                        }
                    }
                    if (!isExist) {
                        listInfo.add(info);
                    }
                }
            }
        }

        return listInfo;
    }

    public DeviceWifiInfo strToWifiStatusInfo(String strInfo) {
        DeviceWifiInfo wifiInfo = new DeviceWifiInfo();
        if (strInfo != null) {
            LogUtils.i("strToWifiStatusInfo:" + strInfo);
            if (strInfo.startsWith(RESULT_TYPE_WIFIINFO)) {
                String[] strArr = strInfo.substring(5).split("#");
                if (strArr.length >= 3) {
                    wifiInfo.setWifiCmd(strArr[0]);
                    wifiInfo.setWifiName(strArr[1]);
                    if (strArr[2].equals("0")){
                        wifiInfo.setConnectStatus(true);
                    }
                }
            }
        }

        return wifiInfo;
    }

    public RecordStatusInfo strToStatusInfo(String strInfo) {
        RecordStatusInfo statusInfo = new RecordStatusInfo();

        if (strInfo != null) {

            if (strInfo.startsWith("NO")) {
                return statusInfo;
            } else {

                statusInfo.setRecordInfo("1");
                String[] strArr = strInfo.split("&");
                for (int i = 0; i < strArr.length; i++) {

                    if (strArr[i].startsWith("ID")) {
                        statusInfo.setRecordId(strArr[i].substring(3));
                    } else if (strArr[i].startsWith("VI")) {
                        statusInfo.setVideoAddr(strArr[i].substring(3));
                    } else if (strArr[i].startsWith("AU")) {
                        statusInfo.setAudioAddr(strArr[i].substring(3));
                    } else if (strArr[i].startsWith("ST")) {
                        statusInfo.setStatusInfo(strArr[i].substring(3));
                    }
                }
            }
        }

        return statusInfo;
    }

    public RecordDeviceInfo strToDeviceInfo(String strInfo) {
        if (recordDeviceInfo == null) {
            recordDeviceInfo = new RecordDeviceInfo();
        }
        if (strInfo != null) {
            String[] strArr = strInfo.split("#");
            String strTemp = "";
            for (int i = 0; i < strArr.length; i++) {
                strTemp = "";
                if (strArr[i].startsWith("WIFI")) {
                    strTemp = strArr[i].substring(5);
                    recordDeviceInfo.setWifiinfo(strToWifiInfo(strTemp));
                } else if (strArr[i].startsWith("RECORD")) {
                    strTemp = strArr[i].substring(7);
                    recordDeviceInfo.setRecordStatus(strToStatusInfo(strTemp));
                } else if (strArr[i].startsWith("UPLOAD")) {
                    strTemp = strArr[i].substring(7);
                    recordDeviceInfo.setUploadset(strTemp);
                } else if (strArr[i].startsWith("SPACE")) {
                    strTemp = strArr[i].substring(6);
                    recordDeviceInfo.setRecordSpace(strTemp);
                } else if (strArr[i].startsWith("POWER")) {
                    strTemp = strArr[i].substring(6);
                    recordDeviceInfo.setRecordPower(strTemp);
                } else if (strArr[i].startsWith("BMAC")) {
                    strTemp = strArr[i].substring(5);
                    recordDeviceInfo.setRecordBlueToothMac(strTemp);
                }
            }
        }

        return recordDeviceInfo;
    }


}
