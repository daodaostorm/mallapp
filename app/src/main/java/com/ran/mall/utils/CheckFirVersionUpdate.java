package com.ran.mall.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.ran.mall.AssessConfig;
import com.ran.mall.https.HttpRequestClient;
import com.ran.mall.entity.bean.UpdateModel;

/**
 * Created by pc on 2017/3/30.
 */
public class CheckFirVersionUpdate {
    private final static String TAG = CheckFirVersionUpdate.class.getSimpleName();

    private final static String ydsUpdateUrl = "https://"+AssessConfig.rb_IP+":"+AssessConfig.rb_port+"/api/lossAss/getUpgradeInfo?packageName=com.ran.mall&versionCode=%s";
    private final static String updateUrl = "https://ttttst/api/lossAss/getUpgradeInfo?packageName=com.ran.mall&versionCode=%s";


    public static CheckFirVersionUpdate mInstance;
    private static String mUpdateurl=updateUrl;

    private CheckFirVersionUpdate() {

    }

    public static CheckFirVersionUpdate getInstance() {
        if (mInstance == null) {
            synchronized (CheckFirVersionUpdate.class) {
                if (mInstance == null) {
                    mInstance = new CheckFirVersionUpdate();
                    if (AssessConfig.isShowOut) {
                        mUpdateurl = ydsUpdateUrl;
                    }
                }
            }
        }
        return mInstance;
    }

    public void checkAssessUpdate(Context context, final CheckUpdateCallBack callBack) {
        String url = String.format(mUpdateurl, getVersionCode(context));
        HttpRequestClient.getIntance().get(url, new HttpRequestClient.RequestHttpCallBack() {
            @Override
            public void onSuccess(String json) {
                if (!TextUtils.isEmpty(json)) {
                    UpdateModel model = new Gson().fromJson(json, UpdateModel.class);
                    String downLoadUrl = model.getDownloadLink();
                    if (!TextUtils.isEmpty(downLoadUrl)) {
                        callBack.isFirNeedUpdate(true, model);
                    } else {
                        callBack.isFirNeedUpdate(false, null);
                    }
                }
            }

            @Override
            public void onFail(String err, int code) {
                callBack.isFirNeedUpdate(false, null);
            }
        });


    }


    public int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (pi != null) {
            return pi.versionCode;
        }
        return -1;
    }


    public interface CheckUpdateCallBack {
        void isFirNeedUpdate(boolean isCanUpdate, UpdateModel model);
    }

}
