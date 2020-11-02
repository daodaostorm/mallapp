package com.ran.mall.https;

import android.os.Looper;
import android.util.Log;

import com.ran.library.util.NetUtil;
import com.ran.mall.config.MainApplication;
import com.ran.mall.entity.constant.Constant;
import com.ran.mall.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by pc on 2017/3/21.
 */
public class HttpRequestClient {
    private static final String TAG = HttpRequestClient.class.getSimpleName();
    private OkHttpClient mOkhttpClient;
    private static HttpRequestClient mInstance;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    //application/x-www-form-urlencoded
    public static final MediaType IMAGE = MediaType.parse("image/jpeg");

    public HttpRequestClient() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        mOkhttpClient = new OkHttpClient().newBuilder()
                .sslSocketFactory(sslContext.getSocketFactory())
                .hostnameVerifier(DO_NOT_VERIFY)
                .connectTimeout(28, TimeUnit.SECONDS)
                .readTimeout(28, TimeUnit.SECONDS)
                .writeTimeout(28, TimeUnit.SECONDS)
                .build();

    }

    public static HttpRequestClient getIntance() {
        if (mInstance == null) {
            synchronized (HttpRequestClient.class) {
                if (mInstance == null) {
                    mInstance = new HttpRequestClient();
                }
            }
        }
        return mInstance;
    }

    //get请求
    public void get(String url, final RequestHttpCallBack callBack) {
        LogUtils.INSTANCE.d(TAG, "get: url" + url);
        int netWorkState = NetUtil.getNetWorkState(MainApplication.getInstance());
        if (netWorkState == NetUtil.NETWORK_NONE) {
            if (callBack != null) {
                LogUtils.INSTANCE.d(TAG, "网络异常,请检查网络");
                callBack.onFail("网络异常,请检查网络!!", -100);
            }
            return;
        }
        Request request = new Request.Builder().url(url).build();
        Call call = mOkhttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.INSTANCE.i(TAG, "onFailure: err" + e.getMessage() + "errCode" + e.hashCode());
                if (callBack != null) {
                    callBack.onFail(e.getMessage(), e.hashCode());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                LogUtils.INSTANCE.i(TAG, "onResponse: result" + result);
                int errCode = -1;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("code")) {
                        errCode = jsonObject.getInt("code");
                        if (errCode != -1) {
                            if (errCode == 0) {
                                if (callBack != null) {
                                    callBack.onSuccess(new JSONObject(result).getString("content"));
                                }
                            } else {
                                if (callBack != null) {
                                    callBack.onFail(new JSONObject(result).getString("message"), errCode);
                                }
                            }
                        } else {
                            if (callBack != null) {
                                callBack.onFail("fail", -1);
                            }
                        }
                    } else {
                        callBack.onSuccess(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onFail("request network fail", -100);
                    }
                }
            }
        });
    }

    //get-token 请求
    public void get_token(String url, String accessToken, final RequestHttpCallBack callBack) {
        LogUtils.INSTANCE.d(TAG, "get_token: url" + url);
        int netWorkState = NetUtil.getNetWorkState(MainApplication.getInstance());
        if (netWorkState == NetUtil.NETWORK_NONE) {
            if (callBack != null) {
                LogUtils.INSTANCE.d(TAG, "网络异常,请检查网络");
                callBack.onFail("网络异常,请检查网络!!", -100);
            }
            return;
        }
        Request request = null;
        if (!accessToken.equals("")) {
            request = new Request.Builder()
                    .url(url).header("token", accessToken).addHeader("token", accessToken)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .build();
        }

        Call call = mOkhttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.INSTANCE.i(TAG, "onFailure: err" + e.getMessage() + "errCode" + e.hashCode());
                if (callBack != null) {
                    callBack.onFail(e.getMessage(), e.hashCode());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                LogUtils.INSTANCE.i(TAG, "onResponse: result" + result);
                int errCode = -1;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("errCode")) {
                        errCode = jsonObject.getInt("code");
                        if (errCode != -1) {
                            if (errCode == 0) {
                                if (callBack != null) {
                                    callBack.onSuccess(new JSONObject(result).getString("content"));
                                }
                            } else {
                                if (callBack != null) {
                                    callBack.onFail(new JSONObject(result).getString("message"), errCode);
                                }
                            }
                        } else {
                            if (callBack != null) {
                                callBack.onFail("fail", -1);
                            }
                        }
                    } else {
                        callBack.onSuccess(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onFail("request network fail", -100);
                    }
                }
            }
        });
    }


    //post请求
    public void post(String url, String json, String aecsssToken, final RequestHttpCallBack callBack) {
        LogUtils.INSTANCE.i(TAG, "post: url" + url);
        LogUtils.INSTANCE.i(TAG, "post: json" + json);

        RequestBody body = RequestBody.create(JSON, json);
        Request request = null;
        if (!aecsssToken.equals("")) {
            request = new Request.Builder()
                    .url(url).header("token", aecsssToken).addHeader("token", aecsssToken)
                    .post(body)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
        }
        Call call = mOkhttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.INSTANCE.i(TAG, "onFailure: errCode" + e.hashCode() + "  e.getMessage()" + e.getMessage());
                if (callBack != null) {
                    callBack.onFail(e.getMessage(), e.hashCode());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                LogUtils.INSTANCE.i(TAG, "onResponse: result" + result);
                int errCode = 0;

                    try {
                    errCode = new JSONObject(result).getInt("code");
                    if (errCode == 0) {
                        if (callBack != null) {
                            if (new JSONObject(result).optString("content") != null) {
                                callBack.onSuccess(new JSONObject(result).optString("content"));
                            } else {
                                callBack.onSuccess("");
                            }
                        }
                    } else {
                        if (callBack != null) {
                            callBack.onFail(new JSONObject(result).getString("message"), errCode);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    if (callBack != null) {
                        callBack.onFail("request network fail", -100);
                    }
                }
            }
        });
    }

    public interface RequestHttpCallBack {
        public void onSuccess(String json);

        public void onFail(String err, int code);
    }

    X509TrustManager xtm = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            X509Certificate[] x509Certificates = new X509Certificate[0];
            return x509Certificates;
        }
    };

}
