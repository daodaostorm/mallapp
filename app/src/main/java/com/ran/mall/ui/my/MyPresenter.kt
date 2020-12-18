package com.ran.mall.ui.my

import android.app.Activity
import com.ran.mall.R
import com.ran.mall.utils.ToastUtils

/**
 * effect：
 */
class MyPresenter(mContext: MyActivity, mView: MyContract.View) : MyContract.Presenter {
    override fun amendPwd() {

    }

    private var mContext: Activity = checkNotNull(mContext)
    private var mView: MyContract.View = checkNotNull(mView)

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