package com.ran.mall.system;

import android.text.TextUtils;
import android.util.Log;

import com.ran.library.base.SystemBase;
import com.ran.mall.AssessConfig;
import com.ran.mall.https.HttpRequestClient;
import com.ran.mall.utils.OcrSign;
import com.ran.mall.utils.ZipUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

//import io.dcloud.common.util.TestUtil;


/**
 * Created by DELL on 2017/5/23.
 */

public class SystemHttpRequest extends SystemBase {

    //测试域名
    public String IP = "";
    public String port = "";

    public String BUSS_IP = "";
    public String BUSS_port = "";

    private String TAG = SystemHttpRequest.class.getSimpleName();

    private String PICC_URL = "/api/lossAss";

    private String MCU_UPGRADE_URL = "http://192.168.10.1/wiced_ota_server";

    @Override
    public void init() {

    }

    public String get_Businiess_Url() {
        if (AssessConfig.isShowOut) {
            BUSS_IP = AssessConfig.rb_BUSS_IP;
            BUSS_port = AssessConfig.rb_BUSS_port;
        } else {
            BUSS_IP = AssessConfig.BUSS_IP;
            BUSS_port = AssessConfig.BUSS_port;
        }
        return "https://" + BUSS_IP + ":" + BUSS_port;
    }

    public String getUri() {
        if (AssessConfig.isShowOut) {
            IP = AssessConfig.rb_IP;
            port = AssessConfig.rb_port;
        } else {
            IP = AssessConfig.IP;
            port = AssessConfig.port;
        }
        return "https://" + IP + ":" + port;
    }

    public void uploadFile(String filePath, HttpRequestClient.RequestHttpCallBack callback) {
        String api = "/uploadLogs";
        StringBuilder builder = new StringBuilder(getUri() + PICC_URL);
        builder.append(api);
        if (TextUtils.isEmpty(filePath)) {
            callback.onFail("upload file is null", -2);
            return;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            callback.onFail("upload file is null", -2);
            return;
        }
        HttpRequestClient.getIntance().postFile(builder.toString(), null, file, callback);
    }


    /**
     * 登录
     **/
    public void responseLogin(String json, HttpRequestClient.RequestHttpCallBack callback) {
        String api = "/api/login";
        StringBuilder builder = new StringBuilder(getUri());
        builder.append(api);
        HttpRequestClient.getIntance().post(builder.toString(), json, "", callback);

    }

    /**
     * 退出
     **/
    public void responseLogout(String json, HttpRequestClient.RequestHttpCallBack callback) {
        String api = "/api/logout";
        StringBuilder builder = new StringBuilder(getUri());
        builder.append(api);
        HttpRequestClient.getIntance().post(builder.toString(), json, "", callback);

    }

    /**
     * 设备
     **/
    public void responseUpdateDevice(String json, HttpRequestClient.RequestHttpCallBack callback) {
        String api = "/api/device";
        StringBuilder builder = new StringBuilder(getUri());
        builder.append(api);
        HttpRequestClient.getIntance().post(builder.toString(), json, "", callback);

    }

    public void getTaskVideoList(int pageSize, int pageIndex, String reportId, HttpRequestClient.RequestHttpCallBack callback) {
        String api = "/api/reportVideo";
        StringBuilder builder = new StringBuilder(getUri());
        builder.append(api + "?pageSize=" + pageSize);
        builder.append("&pageIndex=" + pageIndex);
        builder.append("&reportId=" + reportId);
        HttpRequestClient.getIntance().get(builder.toString(), callback);

    }

    public void getAllTaskVideoList(int pageSize, int pageIndex, HttpRequestClient.RequestHttpCallBack callback) {
        String api = "/api/reportVideo";
        StringBuilder builder = new StringBuilder(getUri());
        builder.append(api + "?pageSize=" + pageSize);
        builder.append("&pageIndex=" + pageIndex);
        HttpRequestClient.getIntance().get(builder.toString(), callback);

    }

    public void getTaskList(String account, int pageSize, int pageIndex, String timeIntervel, HttpRequestClient.RequestHttpCallBack callback) {
        String api = "/api/report/external/list";
        StringBuilder builder = new StringBuilder(get_Businiess_Url());
        builder.append( api + "?searchAgent=" + account);
        builder.append("&pageSize=" + pageSize);
        builder.append("&pageIndex=" + pageIndex);
        builder.append("&ctime=" + timeIntervel);
        builder.append("&orderBy=ctime&order=desc&searchTenant=5f211d52ba7f4400225bd01c");
        HttpRequestClient.getIntance().get(builder.toString(), callback);
    }


    public void getRecordMCUUpdateInfo(String versionCode, HttpRequestClient.RequestHttpCallBack callback) {

        getRecordUpdateInfo("record.wifi", versionCode, callback);

    }

    public void getRecordBlueToothUpdateInfo(String versionCode, HttpRequestClient.RequestHttpCallBack callback) {

        getRecordUpdateInfo("record.bluetooth", versionCode, callback);

    }

    public void getRecordUpdateInfo(String packageName, String versionCode, HttpRequestClient.RequestHttpCallBack callback) {
        String api = getUri() + "/api/upgradeInfo";
        StringBuilder builder = new StringBuilder("");
        builder.append( api + "?packageName=" + packageName);
        builder.append("&versionCode=" + versionCode);
        HttpRequestClient.getIntance().get(builder.toString(), callback);
    }

    /**
     * CreateTask
     **/
    public void responseCreateTask(String json, HttpRequestClient.RequestHttpCallBack callback) {
        String api = "/api/report/external/update";
        StringBuilder builder = new StringBuilder(get_Businiess_Url());
        builder.append(api);
        HttpRequestClient.getIntance().post(builder.toString(), json, "", callback);

    }



    /**
     * 上传
     **/
    public void responseUploadScreenCapture(String json, HttpRequestClient.RequestHttpCallBack callback) {
        String api = "/api/screenCapture";
        StringBuilder builder = new StringBuilder(getUri());
        builder.append(api);
        HttpRequestClient.getIntance().post(builder.toString(), json, "", callback);

    }


    //上传log日志
    public void uploadLogFile(final SystemCommon.onRequestCallBack callBack) {
        getSystem(SystemLogHelper.class).stop();
        String fileName = getSystem(SystemLogHelper.class).mCurrentLogFile;
        Log.d(TAG, "fileName: " + fileName);
        if (fileName == null || fileName.equals("")) {
            Log.d(TAG, "uploadLogFile: file is null");
            if (callBack != null) {
                callBack.onFail("file is empty");
            }
            return;
        }
        File file = new File(fileName);
        if (!file.exists()) {
            if (callBack != null) {
                callBack.onFail("文件不存在");
            }
            return;
        }
        String zipFileName = fileName + ".zip";
        File upFile = null;
        try {
            ZipUtil.zipFolder(fileName, zipFileName);
            upFile = new File(zipFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (upFile == null && upFile.exists()) {
            upFile = file;
        }
        Log.d(TAG, "uploadLogFile: " + upFile.getAbsolutePath());
        uploadFile(upFile.getAbsolutePath(), new HttpRequestClient.RequestHttpCallBack() {
            @Override
            public void onSuccess(String json) {
                Log.d(TAG, "onSuccess: json" + json);
                if (getSystem(SystemLogHelper.class).mLogDumper == null)
                    getSystem(SystemLogHelper.class).start();
                if (callBack != null) {
                    callBack.onSuccess();
                }
            }

            @Override
            public void onFail(String err, int code) {
                Log.d(TAG, "onFail: err:" + err + "  code:" + code);
                if (getSystem(SystemLogHelper.class).mLogDumper == null)
                    getSystem(SystemLogHelper.class).start();
                if (callBack != null) {
                    callBack.onFail(err);
                }

            }
        });
    }


    /**
     * 身份证：idcard
     * 驾驶证：driving
     * 行驶证：vehicle
     * 银行卡：bankcard
     * 车牌号：license_plate
     * VIN码：vin
     */


    public void ocrCarNumber(String octType, String image, HttpRequestClient.RequestHttpCallBack callback) {

        long timestamp = System.currentTimeMillis();
        ArrayList<String> list = new ArrayList<>();
        list.add("type=" + octType);
        list.add("image=" + image);
        list.add("timestamp=" + timestamp);
        String signB = null;
        try {
            String signA = OcrSign.sign(list);
            signB = OcrSign.md5(signA + AssessConfig.OCRSECRET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "ocrCarNumber: sign" + signB);

        HttpRequestClient.getIntance().postOcr(octType, AssessConfig.OCRURL, image, timestamp + "", signB, callback);
    }

    public void ocrVinNumber(String image, HttpRequestClient.RequestHttpCallBack callback) {

        long timestamp = System.currentTimeMillis();
        ArrayList<String> list = new ArrayList<>();
        list.add("type=vin");
        list.add("image=" + image);
        list.add("timestamp=" + timestamp);
        String signB = null;
        try {
            String signA = OcrSign.sign(list);
            signB = OcrSign.md5(signA + AssessConfig.OCRSECRET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "ocrCarNumber: sign" + signB);

        HttpRequestClient.getIntance().postOcrVin(AssessConfig.OCRURL, image, timestamp + "", signB, callback);
    }


}
