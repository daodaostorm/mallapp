package com.ran.mall.ui.setting

import android.app.Activity
import android.util.Log
import com.txt.library.base.SystemManager
import com.ran.mall.R
import com.ran.mall.utils.ToastUtils

/**
 * effect：
 */
class SettingPresenter(mContext: SettingActivity, mView: SettingContract.View) : SettingContract.Presenter {
    override fun amendPwd() {

    }

    private var mContext: Activity = checkNotNull(mContext)
    private var mView: SettingContract.View = checkNotNull(mView)

    init {
        mView.setPresenter(this)
    }

    override fun exit() {

    }

    override fun userLogout() {

    }

    override fun feedback() {

        mContext.runOnUiThread {
            ToastUtils.shortShow(mContext.resources.getString(R.string.wait_message))
        }


    }

    override fun start() {
    }


}