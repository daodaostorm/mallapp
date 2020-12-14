package com.ran.mall.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.ran.library.base.BaseActivity;
import com.ran.library.util.statusbar.StatusBarUtil;
import com.ran.mall.R;
import com.ran.mall.entity.bean.UpdateModel;
import com.ran.mall.system.SystemCommon;
import com.ran.mall.ui.login.LoginActivity;
import com.ran.mall.ui.login.SplashActivity;
import com.ran.mall.ui.update.AppUpdateManager;
import com.ran.mall.utils.ApplicationUtils;
import com.ran.mall.utils.CheckFirVersionUpdate;
import com.ran.mall.utils.PreferenceUtils;
import com.ran.mall.widget.TitleBar;

/**
 * Created by pc on 2017/10/18.
 */

public abstract class BaseActivity_2 extends BaseActivity implements LogoutView, TitleBar.TitleBarListener {
    public TitleBar mTitleBar;

    public boolean isSplsh = false;
    public static final String IS_F0ORCE_LOGOUT = "is_force_logout";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFullScreen();
        if (isNeedTitleBar()) {
            setContentView(R.layout.layout_base_ac);
            View activityLayout = LayoutInflater.from(this).inflate(getLayoutId(), null);
            FrameLayout contentLayout = findViewByIds(R.id.contentLayout);
            contentLayout.addView(activityLayout);
            mTitleBar = findViewByIds(R.id.titleBar);
            if (mTitleBar != null) {
                mTitleBar.setTitleBarListener(this);
            }
            mTitleBar.bringToFront();
        } else {
            setContentView(getLayoutId());
        }
        StatusBarUtil.StatusBarLightMode(this, true);
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary));
        initView();
        initEvent();
    }

    public void setCurrentSplash(boolean isSplsh) {
        this.isSplsh = isSplsh;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getSystem(SystemCommon.class).mLoginFirst && !isSplsh) {
            getSystem(SystemCommon.class).mLoginFirst = false;
            checkUpdate();
        }

    }

    @Override
    public void forceLogout() {

    }

    //是否进行全屏显示
    public void isFullScreen() {

    }

    protected boolean isNeedTitleBar() {
        return true;
    }


    public abstract int getLayoutId();


    public void initView() {

    }

    public void initEvent() {

    }

    @Override
    public void changeWifi() {

    }

    @Override
    public void changeMobile() {

    }

    @Override
    public void changeNetNull() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //getSystem(SystemKandy.class).registerLoginoutCheck(getLocalClassName(), this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public boolean isNeedPermissions(String permissions, int requestCode) {
        boolean isNeedPermissions = ContextCompat.checkSelfPermission(this, permissions) != PackageManager.PERMISSION_GRANTED;
        if (isNeedPermissions) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permissions},
                    requestCode);
        }
        return isNeedPermissions;
    }

    public <T extends View> T findViewByIds(int res) {
        return (T) findViewById(res);
    }

    public <T extends View> T findViewByIds(View view, int res) {
        return (T) view.findViewById(res);
    }

    @Override
    public void responseToTitle() {
    }

    @Override
    public void responseToRightView() {

    }

    @Override
    public void responseToBackView() {

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onBackPressed() {
        responseToBackView();
    }

    @Override
    public void logout() {
        Log.d(TAG, "logout: ");

        PreferenceUtils.clear();
        ApplicationUtils.finishActivity();
        Intent intent = new Intent(BaseActivity_2.this.getApplicationContext(), LoginActivity.class);
        intent.putExtra(IS_F0ORCE_LOGOUT, false);
        startActivity(intent);
    }

    public void setLeftIconShow(boolean isVisible) {
        if (mTitleBar != null) {
            if (isVisible) {
                mTitleBar.getLeftView().setVisibility(View.VISIBLE);
            } else {
                mTitleBar.getLeftView().setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void setLeftViewText(String msg) {
        if (mTitleBar != null) {
            mTitleBar.getLeftTextView().setVisibility(View.VISIBLE);
            mTitleBar.getLeftTextView().setText(msg);
        }
    }

    @Override
    public void responseLeftTxt() {

    }

    @Override
    public void setTitleText(String text) {
        if (mTitleBar != null) {
            mTitleBar.getTitleView().setVisibility(View.VISIBLE);
            mTitleBar.getTitleView().setText(text);
        }
    }

    @Override
    public void setLeftViewIcon(int drawableId) {
        if (mTitleBar != null) {
            mTitleBar.getLeftView().setVisibility(View.VISIBLE);
            mTitleBar.getLeftView().setImageResource(drawableId);
        }
    }

    @Override
    public void setRightViewText(String text) {
        if (mTitleBar != null) {
            mTitleBar.getRightTextView().setVisibility(View.VISIBLE);
            mTitleBar.getRightTextView().setText(text);
        }
    }

    @Override
    public void setRightViewViabile(Boolean isViabile) {
        if (mTitleBar != null) {
            if (isViabile) {
                mTitleBar.getRightTextView().setVisibility(View.VISIBLE);
            } else {
                mTitleBar.getRightTextView().setVisibility(View.INVISIBLE);
            }
        }
    }


    @Override
    public void setRightViewIcon(@DrawableRes int drawableId) {
        if (mTitleBar != null) {
            mTitleBar.getRightImageView().setVisibility(View.VISIBLE);
            mTitleBar.getRightImageView().setImageResource(drawableId);
        }
    }

    public TitleBar getTitleBar() {
        return mTitleBar;
    }

    public void setTitleBar(TitleBar titleBar) {
        this.mTitleBar = titleBar;
        if (mTitleBar != null) {
            mTitleBar.setTitleBarListener(this);
        }
    }


    //检查版本更新
    public void checkUpdate() {
        CheckFirVersionUpdate.getInstance().checkAssessUpdate(BaseActivity_2.this, new CheckFirVersionUpdate.CheckUpdateCallBack() {
            @Override
            public void isFirNeedUpdate(boolean isCanUpdate, final UpdateModel model) {
                if (isCanUpdate) {
                    BaseActivity_2.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!AppUpdateManager.getInstance(BaseActivity_2.this, SplashActivity.class).isDownStart()) {
                                AppUpdateManager.getInstance(BaseActivity_2.this, SplashActivity.class).checkUpdate(model);
                            }
                        }
                    });
                }
            }
        });
    }

    public boolean checkVideoOrAudioPermissionOpen() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    //后台线程进行上传图片


}
