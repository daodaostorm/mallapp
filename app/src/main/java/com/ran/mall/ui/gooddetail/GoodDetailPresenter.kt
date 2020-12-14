package com.ran.mall.ui.gooddetail

import android.app.Activity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ran.library.base.SystemManager
import com.ran.mall.entity.bean.GoodInfo
import com.ran.mall.https.HttpRequestClient
import com.ran.mall.system.SystemHttpRequest
import org.json.JSONObject

/**
 */
class GoodDetailPresenter(mContext: GoodDetailActivity, mView: GoodDetailContract.View) : GoodDetailContract.Presenter {


    override fun getGoodDetailData(goodId: String) {

    }

    override fun start() {

    }


    private var mContext: Activity = checkNotNull(mContext)
    private var mView: GoodDetailContract.View = checkNotNull(mView)
    private var mListGoodDatas = ArrayList<GoodInfo>()

    init {
        mView.setPresenter(this)
    }

    override fun exit() {
    }


}