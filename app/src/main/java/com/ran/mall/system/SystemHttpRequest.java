package com.ran.mall.system;

import com.ran.library.base.SystemBase;
import com.ran.mall.AssessConfig;
import com.ran.mall.https.HttpRequestClient;

/**
 */

public class SystemHttpRequest extends SystemBase {

    //域名
    public String IP = "";
    public String port = "";

    private String TAG = SystemHttpRequest.class.getSimpleName();

    private String API_URL = "/api/v1";

    @Override
    public void init() {

    }

    public String getUri() {
        if (AssessConfig.isShowOut) {
            IP = AssessConfig.rb_IP;
            port = AssessConfig.rb_port;
        } else {
            IP = AssessConfig.IP;
            port = AssessConfig.port;
        }
        return "http://" + IP + ":" + port;
    }

    /**
     * 登录
     **/
    public void responseLogin(String json, HttpRequestClient.RequestHttpCallBack callback) {
        String api = "/clientuser/login";
        StringBuilder builder = new StringBuilder(getUri() + API_URL);
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


}
