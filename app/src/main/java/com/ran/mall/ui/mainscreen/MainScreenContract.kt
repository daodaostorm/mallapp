package com.ran.mall.ui.mainscreen

import com.ran.mall.base.BasePresenterItf
import com.ran.mall.base.BaseViewT
import com.ran.mall.entity.bean.BannerInfo
import com.ran.mall.entity.bean.EssayInfo

/**
 */

interface MainScreenContract {


    interface View : BaseViewT<Presenter> {

        fun requestFail(errCode: Int, errMsg: String)
        fun requestSuccess(listInfo: ArrayList<EssayInfo>)

        fun requestBannerFail(errCode: Int, errMsg: String)
        fun requestBannerSuccess(listInfo: ArrayList<BannerInfo>)
    }

    interface Presenter : BasePresenterItf {
        
		fun exit() //退出

        fun getListEssayData()

        fun getBannerListData()

    }
}