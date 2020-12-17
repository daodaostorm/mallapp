package com.ran.mall.ui.essaydetail

import android.app.Activity
import com.google.gson.Gson
import com.ran.mall.entity.bean.EssayInfo

/**
 */
class EssayDetailPresenter(mContext: EssayDetailActivity, mView: EssayDetailContract.View) : EssayDetailContract.Presenter {

    override fun getEssayDetail(essayId: String) {

        val essayinfo = Gson().fromJson(essayId, EssayInfo::class.java);
        mView?.requestSuccess(essayinfo)

    }


    override fun start() {

    }


    private var mContext: Activity = checkNotNull(mContext)
    private var mView: EssayDetailContract.View = checkNotNull(mView)

    init {
        mView.setPresenter(this)
    }

    override fun exit() {
    }


}