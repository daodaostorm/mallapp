package com.ran.mall.ui.gooddetail

import android.app.Activity
import com.ran.mall.entity.bean.GoodInfo

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