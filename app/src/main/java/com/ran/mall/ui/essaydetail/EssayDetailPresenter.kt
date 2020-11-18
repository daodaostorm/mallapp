package com.ran.mall.ui.essaydetail

import android.app.Activity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ran.library.base.SystemManager
import com.ran.mall.entity.bean.BannerInfo
import com.ran.mall.entity.bean.EssayInfo
import com.ran.mall.entity.bean.UserBean
import com.ran.mall.https.HttpRequestClient
import com.ran.mall.system.SystemHttpRequest
import com.ran.mall.ui.mainscreen.MainScreenActivity
import com.ran.mall.ui.mainscreen.MainScreenContract
import com.ran.mall.utils.PreferenceUtils
import org.json.JSONObject

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