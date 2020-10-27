package com.ran.mall.https;

import android.os.Looper;
import android.util.Log;

import com.txt.library.util.NetUtil;
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

    public void postFile(final String uri, final Map<String, Object> map, final File file, final RequestHttpCallBack callBack) {
        LogUtils.INSTANCE.i(TAG, "postFile: uri" + uri);
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MediaType.parse("file/*"), file);
            String filename = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("file", file.getName(), body);
        }
        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {
                requestBody.addFormDataPart(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder().url(uri).post(requestBody.build()).build();
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
                LogUtils.INSTANCE.i(TAG, "onResponse: " + result);
                if (response.isSuccessful()) {


                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.has("errCode")) {
                            String errCode = jsonObject.getString("errCode");
                            if (errCode.equals("0")) {
                                if (callBack != null) {
                                    callBack.onSuccess(new JSONObject(result).getString("result"));
                                }
                            } else {
                                if (callBack != null) {
                                    callBack.onFail("request Fail", -1);
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (callBack != null) {
                            callBack.onFail("request Fail", -100);
                        }
                    }

                } else {
                    if (callBack != null) {
                        callBack.onFail("request Fail", -1);
                    }
                }

            }
        });


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
                String errCode = null;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("errCode")) {
                        errCode = jsonObject.getString("errCode");
                        if (errCode != null) {
                            if (errCode.equals("0")) {
                                if (callBack != null) {
                                    callBack.onSuccess(new JSONObject(result).getString("result"));
                                }
                            } else {
                                if (callBack != null) {
                                    callBack.onFail(new JSONObject(result).getString("errInfo"), Integer.parseInt(errCode));
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

    public void put(String url, String json, String aecsssToken, final RequestHttpCallBack callBack) {
        LogUtils.INSTANCE.d(TAG, "put: url" + url);
        LogUtils.INSTANCE.d(TAG, "put: json" + json);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = null;
        if (!aecsssToken.equals("")) {
            request = new Request.Builder()
                    .url(url).header("access-token", aecsssToken).addHeader("token", aecsssToken)
                    .put(body)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .put(body)
                    .build();
        }
        Call call = mOkhttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    callBack.onFail(e.getMessage(), e.hashCode());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                LogUtils.INSTANCE.i(TAG, "onResponse: result" + result);
//                LogUtils.INSTANCE.i(TAG,"onResponse: result" + result);
                String errCode = null;
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    errCode = jsonObject.getString("errCode");
                    if (errCode != null && errCode.equals("0")) {
                        if (callBack != null) {
                            if (jsonObject.has("result")) {
                                callBack.onSuccess(jsonObject.getString("result"));
                            } else {
                                callBack.onSuccess("");
                            }
                        }
                    } else {
                        if (callBack != null) {
                            callBack.onFail(new JSONObject(result).getString("errInfo"), Integer.parseInt(errCode));
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

    //post请求
    public void post(String url, String json, String aecsssToken, final RequestHttpCallBack callBack) {
        LogUtils.INSTANCE.i(TAG, "post: url" + url);
        LogUtils.INSTANCE.i(TAG, "post: json" + json);
//        int netWorkState = NetUtil.getNetWorkState(MainApplication.getInstance());
//        if (netWorkState == NetUtil.NETWORK_NONE) {
//            if (callBack != null) {
//                LogUtils.INSTANCE.d(TAG, "网络异常,请检查网络");
//                callBack.onFail("网络异常,请检查网络", -100);
//            }
//            return;
//        }
        RequestBody body = RequestBody.create(JSON, json);
        Request request = null;
        if (!aecsssToken.equals("")) {
            request = new Request.Builder()
                    .url(url).header("access-token", aecsssToken).addHeader("token", aecsssToken)
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
                String errCode = null;

                try {
                    errCode = new JSONObject(result).getString("errCode");
                    if (errCode != null && errCode.equals("0")) {
                        if (callBack != null) {
                            if (new JSONObject(result).optString("result") != null) {
                                callBack.onSuccess(new JSONObject(result).optString("result"));
                            } else {
                                callBack.onSuccess("");
                            }
                        }
                    } else {
                        if (callBack != null) {
                            callBack.onFail(new JSONObject(result).getString("errInfo"), Integer.parseInt(errCode));
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

    public void postDongSheng(String url, String json, String aecsssToken, final RequestHttpCallBack callBack) {
        Log.d(TAG, "post: url:" + url);
        Log.d(TAG, "post: json:" + json);
//        MyLogUtils.INSTANCE.i(TAG,"post: url " + url);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = null;
        if (!aecsssToken.equals("")) {
            request = new Request.Builder()
                    .url(url).header("access-token", aecsssToken).addHeader("token", aecsssToken)
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
                Log.d(TAG, "onFailure: errCode" + e.hashCode() + "  e.getMessage()" + e.getMessage());
                if (callBack != null) {
                    callBack.onFail(e.getMessage(), e.hashCode());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d(TAG, "onResponse: result" + result);
//                MyLogUtils.INSTANCE.i(TAG,"onResponse: result" + result);
                boolean b = Looper.getMainLooper() == Looper.myLooper();
                Log.d(TAG, "isMainThread" + b);
                String token = null;

                try {
                    token = new JSONObject(result).getString("access_token");
                    if (token != null && token.length() > 5) {
                        if (callBack != null) {
                            callBack.onSuccess(result);
                        }
                    } else {
                        if (callBack != null) {
                            callBack.onFail(result, -1);
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

    //post请求
    public void postTest(String url, String json, final RequestHttpCallBack callBack) {
        Log.i(TAG, "post: url" + url);
        Log.i(TAG, "post: json" + json);

        RequestBody body = RequestBody.create(IMAGE, json);
        Request request = null;

        request = new Request.Builder()
                    .url(url).header("MCP-X-TOKEN", "593eebefe5044d9eb921f920836afbf5")
                    .addHeader("registNo","18a76d2870b54e4bba444ec24a75db55")
                .addHeader("phoneNum","2")
                .addHeader("phoneType","1")
                .addHeader("photoNum","2")
                    .post(body)
                    .build();
        Log.d(TAG, "post: request.toString()" + request.toString());
        Call call = mOkhttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: errCode" + e.hashCode() + "  e.getMessage()" + e.getMessage());
                if (callBack != null) {
                    callBack.onFail(e.getMessage(), e.hashCode());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d(TAG, "onResponse: result" + result);
                String errCode = null;

                try {
                    errCode = new JSONObject(result).getString("errCode");
                    if (errCode != null && errCode.equals("0")) {
                        if (callBack != null) {
                            callBack.onSuccess(new JSONObject(result).getString("result"));
                        }
                    } else {
                        if (callBack != null) {
                            callBack.onFail(new JSONObject(result).getString("errInfo"), Integer.parseInt(errCode));
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

    public void postDongShengReport(String url, String json, String aecsssToken, final RequestHttpCallBack callBack) {
        Log.d(TAG, "post: url:" + url);
        Log.d(TAG, "post: json:" + json);
//        MyLogUtils.INSTANCE.i(TAG,"post: url " + url);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = null;
        if (!aecsssToken.equals("")) {
            request = new Request.Builder()
                    .url(url).addHeader("Authorization", aecsssToken)
                    .post(body)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
        }
        Log.d(TAG, "post: request.toString()" + request.toString());
        Call call = mOkhttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: errCode" + e.hashCode() + "  e.getMessage()" + e.getMessage());
                if (callBack != null) {
                    callBack.onFail(e.getMessage(), e.hashCode());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d(TAG, "onResponse: result" + result);
//                MyLogUtils.INSTANCE.i(TAG,"onResponse: result" + result);
                boolean b = Looper.getMainLooper() == Looper.myLooper();
                Log.d(TAG, "isMainThread" + b);
                JSONObject jsonBody = null;
                try {
                    jsonBody = new JSONObject(result).getJSONObject("Packet").getJSONObject("Body");
                    if (jsonBody != null) {
                        String strReposeCode = jsonBody.getString("responseCode");
                        if (callBack != null) {
                            if (strReposeCode.equals("000")) {
                                callBack.onSuccess(jsonBody.toString());
                            } else {
                                callBack.onFail(jsonBody.toString(), -1);
                            }
                        }
                    } else {
                        if (callBack != null) {
                            callBack.onFail(result, -1);
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

    public void postDongShengCommon(String url, String json, String aecsssToken, final RequestHttpCallBack callBack) {
        Log.d(TAG, "post: url:" + url);
        Log.d(TAG, "post: json:" + json);
//        MyLogUtils.INSTANCE.i(TAG,"post: url " + url);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = null;
        if (!aecsssToken.equals("")) {
            request = new Request.Builder()
                    .url(url).addHeader("Authorization", aecsssToken)
                    .post(body)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
        }
        Log.d(TAG, "post: request.toString()" + request.toString());
        Call call = mOkhttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: errCode" + e.hashCode() + "  e.getMessage()" + e.getMessage());
                if (callBack != null) {
                    callBack.onFail(e.getMessage(), e.hashCode());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d(TAG, "onResponse: result" + result);
//                MyLogUtils.INSTANCE.i(TAG,"onResponse: result" + result);
                boolean b = Looper.getMainLooper() == Looper.myLooper();
                JSONObject jsonHead = null;
                JSONObject jsonBody = null;
                try {
                    jsonHead = new JSONObject(result).getJSONObject("LossRtnReturnData").getJSONObject("LossRtnHead");
                    Log.d(TAG, "jsonHead" + jsonHead.toString());
                    if (jsonHead != null) {
                        String strReposeCode = jsonHead.getString("responseCode");
                        Log.d(TAG, "strReposeCode " + strReposeCode);
                        if (callBack != null) {
                            if (!strReposeCode.equals("0")) {
                                jsonBody = new JSONObject(result).getJSONObject("LossRtnReturnData").getJSONObject("LossRtnBody");
                                Log.d(TAG, "jsonBody " + jsonBody.toString());
                                callBack.onSuccess(jsonBody.toString());
                            } else {
                                callBack.onFail(strReposeCode, -1);
                            }
                        }
                    } else {
                        if (callBack != null) {
                            callBack.onFail(result, -1);
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


    public void postOcr(String type, String url, String image, String timestamp, String sign, final RequestHttpCallBack callBack) {
        Log.d(TAG, "post: url" + url);
        Log.d(TAG, "post: json" + image);
        FormBody body = new FormBody.Builder()
                .add("type", type)
                .add("image", image)
                .add("timestamp", timestamp)
                .add("sign", sign)
                .build();

        Request request = null;
        request = new Request.Builder()
                .url(url).addHeader("Authorization", "Bearer TxthRD2xvdz5Ds2YkDH")
                .post(body)
                .build();

        Log.d(TAG, "post: request.toString()" + request.toString());
        Call call = mOkhttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: errCode" + e.hashCode() + "  e.getMessage()" + e.getMessage());
                if (callBack != null) {
                    callBack.onFail(e.getMessage(), e.hashCode());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d(TAG, "onResponse: result" + result);
                try {
                    JSONObject object = new JSONObject(result);
                    int errCode = object.getInt("statusCode");
                    if (errCode == 200) {
                        JSONObject array = object.getJSONObject("response");
                        String carNumber = "";
                        if (type.equals(Constant.OCRType.license_plate)) {
                            carNumber = array.getString("number");
                        } else if (type.equals(Constant.OCRType.idcard)) {
                            carNumber = array.getString("id");
                            if (carNumber.isEmpty()) { //
                                if (callBack != null) {
                                    callBack.onFail("查询失败", -1);
                                    return;
                                }
                            }


                        }

                        if (callBack != null) {
                            callBack.onSuccess(carNumber);
                        }
                    } else {
                        if (callBack != null) {
                            JSONObject array = object.getJSONObject("error");
                            String errMsg = array.getString("errorInfo");
                            callBack.onFail(errMsg, -1);
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

    public void postOcrVin(String url, String image, String timestamp, String sign,
                           final RequestHttpCallBack callBack) {
        Log.d(TAG, "post: url" + url);
        Log.d(TAG, "post: json" + image);
        FormBody body = new FormBody.Builder()
                .add("type", "vin")
                .add("image", image)
                .add("timestamp", timestamp)
                .add("sign", sign)
                .build();

        Request request = null;
        request = new Request.Builder()
                .url(url).addHeader("Authorization", "Bearer TxthRD2xvdz5Ds2YkDH")
                .post(body)
                .build();

        Log.d(TAG, "post: request.toString()" + request.toString());
        Call call = mOkhttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: errCode" + e.hashCode() + "  e.getMessage()" + e.getMessage());
                if (callBack != null) {
                    callBack.onFail(e.getMessage(), e.hashCode());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d(TAG, "onResponse: result" + result);
                try {
                    JSONObject object = new JSONObject(result);
                    int errCode = object.getInt("statusCode");
                    if (errCode == 200) {
                        JSONObject array = object.getJSONObject("response");
                        String vinNumber = array.getString("vin");
                        if (callBack != null) {
                            callBack.onSuccess(vinNumber);
                        }
                    } else {
                        if (callBack != null) {
                            JSONObject array = object.getJSONObject("error");
                            String errMsg = array.getString("errorInfo");
                            callBack.onFail(errMsg, -1);
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

    public void delect(String url, final RequestHttpCallBack callBack) {
//        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        Call call = mOkhttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    callBack.onFail(e.getMessage(), e.hashCode());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d(TAG, "onResponse: result" + result);
//                LogUtils.INSTANCE.i(TAG,"onResponse: result" + result);
                String errCode = null;
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    errCode = jsonObject.getString("errCode");
                    if (errCode != null && errCode.equals("0")) {
                        if (callBack != null) {
                            if (jsonObject.has("result")) {
                                callBack.onSuccess(jsonObject.getString("result"));
                            } else {
                                callBack.onSuccess("");
                            }
                        }
                    } else {
                        if (callBack != null) {
                            callBack.onFail(new JSONObject(result).getString("errInfo"), Integer.parseInt(errCode));
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
