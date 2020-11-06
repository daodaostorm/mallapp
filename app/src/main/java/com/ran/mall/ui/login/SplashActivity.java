package com.ran.mall.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ran.mall.AssessConfig;
import com.ran.mall.R;
import com.ran.mall.base.BaseActivity_2;
import com.ran.mall.entity.bean.UserRequestMoudle;
import com.ran.mall.entity.constant.Constant;
import com.ran.mall.system.SystemCommon;
import com.ran.mall.system.SystemHttpRequest;
import com.ran.mall.system.SystemLogHelper;
import com.ran.mall.ui.main.MainActivity;
import com.ran.mall.ui.mainscreen.MainScreenActivity;
import com.ran.mall.utils.ApkUtils;
import com.ran.mall.utils.PreferenceUtils;
import com.ran.mall.widget.ShowIpControlDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/10/18.
 */

public class SplashActivity extends BaseActivity_2 implements LoginContract.View {
    private LoginPresenter mLoginPreSenter;
    private final String TAG = SplashActivity.class.getSimpleName();
    private String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION
            , Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN};
    List<String> mPermissionList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String model = android.os.Build.BRAND;
        Log.d(TAG, "onCreate: model" + model);
        PreferenceUtils.init(this);
        setCurrentSplash(true);
        getSystem(SystemCommon.class).mMoblieModle = model;
        if (!this.isTaskRoot()) {
            finish();

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        String version;
        if (AssessConfig.isShowOut)
            version = ApkUtils.getAppVersionName(SplashActivity.this, SplashActivity.this.getPackageName());
        else
            version = ApkUtils.getAppVersionName(SplashActivity.this, SplashActivity.this.getPackageName()) + AssessConfig.CURRENTDEBUGVERSION;
        ((TextView) findViewByIds(R.id.version)).setText(version);
    }

    @Override
    public void initEvent() {
        mLoginPreSenter = new LoginPresenter(this, this);


        LinearLayout mView = findViewByIds(R.id.bg);
        AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
        aa.setDuration(500);
        mView.setAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (AssessConfig.isShowIpDialog) {
                    getSystem(SystemCommon.class).showIPDialog(SplashActivity.this, new ShowIpControlDialog.onDialogListenerCallBack() {
                        @Override
                        public void onOkCliclck(String ip, String port) {
                            if (!ip.equals("") && !port.equals("")) {
                                getSystem(SystemHttpRequest.class).IP = ip;
                                getSystem(SystemHttpRequest.class).port = port;
                            }
                            startIntoMain();
                        }
                    });
                } else {
                    startIntoMain();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }

    //页面跳转
    public void startIntoMain() {
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        if (mPermissionList.isEmpty()) {
            Log.d(TAG, " no permiss is request");
            getSystem(SystemLogHelper.class).start();


            Intent intent = new Intent(SplashActivity.this, MainScreenActivity.class);
            startActivity(intent);
            finish();

        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: requestCode" + requestCode);
        switch (requestCode) {
            case 1:
                getSystem(SystemLogHelper.class).start();
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

                break;
            default:
                break;

        }
    }

    @Override
    protected boolean isNeedTitleBar() {
        return false;
    }

    @Override
    public void changeNetNull() {

    }


    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }


    @Override
    public void LoginSuccess() {
        runOnUiThread(new Runnable() {
            @Override public void run() {
                /*PreferenceUtils.setLogin(true);

                Intent intent = new Intent(SplashActivity.this, SurveyMainActivity.class);
                startActivity(intent);
                finish();*/
            }
        });
    }

    @Override
    public void LoginBFail(String err, int errCode) {
        Log.d(TAG, "LoginBFail: ");
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public SystemHttpRequest getSystemRequest() {
        return getSystem(SystemHttpRequest.class);
    }

    @Override
    public String getRequestBody() {
        UserRequestMoudle model = PreferenceUtils.getUserRequestData();
        if (model != null) {
            return new Gson().toJson(model);
        }

        return null;
    }

    @Override
    public void saveNameAndPwd() {

    }
}
