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
import com.ran.mall.system.SystemCommon;
import com.ran.mall.system.SystemHttpRequest;
import com.ran.mall.system.SystemLogHelper;
import com.ran.mall.ui.mainscreen.MainScreenActivity;
import com.ran.mall.utils.ApkUtils;
import com.ran.mall.utils.LogUtils;
import com.ran.mall.utils.PreferenceUtils;
import com.ran.mall.widget.ShowIpControlDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/10/18.
 */

public class SplashActivity extends BaseActivity_2 {
    private final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String model = android.os.Build.BRAND;
        LogUtils.i(TAG + "onCreate: model" + model);
        PreferenceUtils.init(this);
        setCurrentSplash(true);
        getSystem(SystemCommon.class).mMoblieModle = model;
        if (!this.isTaskRoot()) {
            finish();

        }
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

        LinearLayout mView = findViewByIds(R.id.bg);
        AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
        aa.setDuration(5000);
        mView.setAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                    startIntoMain();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }

    //页面跳转
    public void startIntoMain() {

            getSystem(SystemLogHelper.class).start();
            Intent intent = new Intent(SplashActivity.this, MainScreenActivity.class);
            startActivity(intent);
            finish();

    }

    @Override
    protected boolean isNeedTitleBar() {
        return false;
    }

    @Override
    public void changeNetNull() {

    }

}
