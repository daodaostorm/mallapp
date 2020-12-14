package com.ran.mall.ui.gooddetail

import com.ran.mall.base.BasePresenterItf
import com.ran.mall.base.BaseViewT
import com.ran.mall.entity.bean.GoodInfo

/**
 */

interface GoodDetailContract {


    interface View : BaseViewT<Presenter> {

        fun requestFail(errCode: Int, errMsg: String)
        fun requestSuccess(listInfo: ArrayList<GoodInfo>)
    }

    interface Presenter : BasePresenterItf {
        
		fun exit() //退出

        fun getGoodDetailData(goodId: String)

    }
}